package Bitopiary.ExecutionState

import Extensions.toInt
import Bitopiary.Instructions.ConditionalInstruction
import Bitopiary.Logger
import java.util.*

class ConditionalStack(private val environment : ExecutionTrack) {

    data class Marker(val startInstruction: ConditionalInstruction, var startValue: Int) {

        var toBePaired: ConditionalInstruction? = startInstruction
        var conditionTrue = false
        var finished = false
        var awaitingCondition = true
        var clauseCounter = 0

        fun matches(other: ConditionalInstruction): Boolean = false != toBePaired?.type?.matches(other.type) //Considered match if none to match with

        operator fun component3() = toBePaired
        operator fun component4() = conditionTrue
        operator fun component5() = awaitingCondition

    }

    private val stack = Stack<Marker>()

    fun executeConditionalInstruction(instruction: ConditionalInstruction) {
        when (stack.isEmpty() || !stack.peek().matches(instruction)) {
            true -> if (!environment.isRestricted()) startConditionalChain(instruction)
            false -> continueConditionalChain(instruction)
        }
    }

    private fun startConditionalChain(instruction: ConditionalInstruction) {
        val relevantValue = when (instruction.modifyInputChannel) {
            true -> instruction.input.toInt()
            false -> environment.getInt()
        }
        stack.add(Marker(instruction, relevantValue))
    }

    private fun continueConditionalChain(nextInstruction: ConditionalInstruction){
        val marker = stack.peek()
        val (startInstruction, _, toBePaired, conditionTrue, awaitingCondition) = marker

        val startPair = null == toBePaired
        val waitingForFinish = marker.finished
        val last = toBePaired?.type == startInstruction.type && marker.clauseCounter != 0

        when {
            last -> finishUpChain()
            waitingForFinish -> restrictInstructions(startPair)
            startPair -> handleStartPair(awaitingCondition, conditionTrue)
            null != toBePaired -> handleEndPair(awaitingCondition, conditionTrue, marker, toBePaired, nextInstruction)
        }

        if (startPair){
            marker.toBePaired = nextInstruction
        } else {
            marker.toBePaired = null
            marker.clauseCounter++
        }
    }

    private fun finishUpChain() {
        environment.enableInstructions()
        val over = stack.pop()
        Logger.l(environment, "ConditionalStack conditional ended: ${over.clauseCounter}")
    }

    private fun handleEndPair(awaitingCondition: Boolean, conditionTrue: Boolean, marker: Marker, startPaired: ConditionalInstruction, endPaired: ConditionalInstruction) {
        if (awaitingCondition){
            val valueOfEnd = endPaired.getValue(environment.getInt())
            marker.conditionTrue = true == startPaired.evaluateConditional(marker.startValue, valueOfEnd)
            Logger.l(environment, "ConditionalStack evaluated: ${marker.conditionTrue}")
        } else if (conditionTrue) {
            marker.finished = true
        } else {
            environment.enableInstructions()
        }
        marker.awaitingCondition = !awaitingCondition
    }

    private fun handleStartPair(awaitingCondition: Boolean, conditionTrue: Boolean) {
        if(awaitingCondition){
            //Do nothing
        } else if (!conditionTrue){
            environment.restrictInstructions()
        }
    }

    private fun restrictInstructions(startPair: Boolean) {
        if(startPair){
            environment.restrictInstructions()
        } else {
            environment.enableInstructions()
        }
    }

}