package Bitopiary.ExecutionState
import Bitopiary.Logger
import Bitopiary.Instructions.*
import java.util.*
import Extensions.*


class LoopStack(private val environment : ExecutionTrack) {

    data class LoopMarker(var counter: Int, val instruction: BracketInstruction, var value: Int, val startLoopCaret: Caret) {

        fun matches(other: BracketInstruction): Boolean = other.type.matches(instruction.type)

        fun increaseLoopCounter() {
            counter++
        }
    }

    private val stack = Stack<LoopMarker>()

    fun executeLoopInstruction(instruction: BracketInstruction) = when (stack.isEmpty() || !stack.peek().matches(instruction)) {
        true -> startLoopIteration(instruction)
        false -> endLoopIteration(instruction)
    }

    private fun startLoopIteration(instruction: BracketInstruction) {
        val relevantValue = when (instruction.modifyInputChannel) {
            true -> instruction.input.toInt()
            false -> environment.getInt()
        }

        if (instruction.doZeroIterations(relevantValue)){
            environment.restrictInstructions(instruction.type)
        }

        stack.add(LoopMarker(1, instruction, relevantValue, environment.getExecutionCaretPosition()))

    }

    private fun endLoopIteration(endInstruction: BracketInstruction){
        val (counter, startInstruction, valueOfStart, caret) = stack.peek()

        val valueOfEnd = endInstruction.getValue(environment.getInt())
        if (startInstruction.conditionMet(counter, valueOfStart, valueOfEnd)){
            stack.pop()
            environment.enableInstructions()
            Logger.l(environment, "Exiting Loop at $counter iterations")
        } else {
            stack.peek().increaseLoopCounter()
            environment.setExecutionCaret(caret)
        }
    }
}