package Bitopiary.ExecutionState

import Extensions.toInt
import Bitopiary.Instructions.ConditionalInstruction
import Bitopiary.Logger
import Bitopiary.OperatorType
import java.util.*

class ConditionalStack(private val environment : ExecutionTrack) {

    interface RestrictionTarget{

        fun enableInstructions()
        fun restrictInstructions(source: OperatorType)
    }

    data class Marker(val startInstruction: ConditionalInstruction, var startValue: Int, val restrictionTarget: RestrictionTarget) {

        var toBePaired: ConditionalInstruction? = startInstruction
        var conditionTrue = false
        var finished = false
        var awaitingCondition = true
        var clauseCounter = 0

        fun matches(other: ConditionalInstruction): Boolean = false != toBePaired?.type?.matches(other.type) //Considered match if none to match with

        operator fun component4() = toBePaired
        operator fun component5() = conditionTrue
        operator fun component6() = awaitingCondition

    }

    private val stack = Stack<Marker>()

    fun executeConditionalInstruction(instruction: ConditionalInstruction) {
        when (stack.isEmpty() || !stack.peek().matches(instruction)) {
            true -> startConditionalChain(instruction)
            false -> continueConditionalChain(instruction)
        }
    }

    private fun startConditionalChain(instruction: ConditionalInstruction) {
        val relevantValue = when (instruction.modifyInputChannel) {
            true -> instruction.input.toInt()
            false -> environment.getInt()
        }
        if (environment.isRestricted()){
            stack.add(Marker(instruction, relevantValue, object : RestrictionTarget {
                /**Conditional chain is restricted and cannot affect anything*/
                override fun enableInstructions() {}
                override fun restrictInstructions(source: OperatorType) {}
            }))
        } else {
            stack.add(Marker(instruction, relevantValue, environment))
        }
    }

    private fun continueConditionalChain(nextInstruction: ConditionalInstruction){
        val marker = stack.peek()
        val (startInstruction, _, _, toBePaired, conditionTrue, awaitingCondition) = marker

        val startPair = null == toBePaired
        val waitingForFinish = marker.finished
        val last = toBePaired?.type == startInstruction.type && marker.clauseCounter != 0

        when {
            last -> finishUpChain(marker)
            waitingForFinish -> restrictInstructions( marker, startPair, nextInstruction)
            startPair -> handleStartPair( marker, awaitingCondition, conditionTrue, nextInstruction)
            null != toBePaired -> handleEndPair( marker, awaitingCondition, conditionTrue, toBePaired, nextInstruction)
        }

        if (startPair){
            marker.toBePaired = nextInstruction
        } else {
            marker.toBePaired = null
            marker.clauseCounter++
        }
    }

    private fun finishUpChain(marker: Marker) {
        marker.restrictionTarget.enableInstructions()
        val over = stack.pop()
        Logger.l(environment, "ConditionalStack conditional ended: ${over.clauseCounter}")
    }

    private fun handleEndPair(marker: Marker, awaitingCondition: Boolean, conditionTrue: Boolean, startPaired: ConditionalInstruction, endPaired: ConditionalInstruction) {
        if (awaitingCondition){
            val valueOfEnd = endPaired.getValue(environment.getInt())
            marker.conditionTrue = true == startPaired.evaluateConditional(marker.startValue, valueOfEnd)
            Logger.l(environment, "ConditionalStack evaluated: ${marker.conditionTrue}")
        } else if (conditionTrue) {
            marker.finished = true
        } else {
            marker.restrictionTarget.enableInstructions()
        }
        marker.awaitingCondition = !awaitingCondition
    }

    private fun handleStartPair(marker: Marker, awaitingCondition: Boolean, conditionTrue: Boolean, instruction: ConditionalInstruction) {
        if(awaitingCondition){
            //Do nothing
        } else if (!conditionTrue){
            marker.restrictionTarget.restrictInstructions(instruction.type)
        }
    }

    private fun restrictInstructions(marker: Marker, startPair: Boolean, instruction: ConditionalInstruction) {
        if(startPair){
            marker.restrictionTarget.restrictInstructions(instruction.type)
        } else {
            marker.restrictionTarget.enableInstructions()
        }
    }

}