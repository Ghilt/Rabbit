
import Instructions.*
import java.util.*
import Extensions.*


class LoopStack(private val environment :ExecutionTrack) {

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
//        val relevantValue = if (instruction.modifyInputChannel) environment.getInt() else instruction.input.toInt()
        val relevantValue = when (instruction.modifyInputChannel) {
            true -> instruction.input.toInt()
            false -> environment.getInt()
        }
        stack.add(LoopMarker(0, instruction, relevantValue, environment.getExecutionCaretPosition()))
    }

    private fun endLoopIteration(endInstruction: BracketInstruction){
        val (counter, startInstruction, valueOfStart, caret) = stack.peek()

        val valueOfEnd = endInstruction.getValue(environment.getInt())
        if (startInstruction.conditionMet(counter, valueOfStart, valueOfEnd)){
            stack.pop()
        } else {
            stack.peek().increaseLoopCounter()
            environment.setExecutionCaret(caret)
        }

    }
}