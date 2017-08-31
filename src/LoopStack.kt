
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

    private fun startLoopIteration(instruction: BracketInstruction) = when (instruction.modifyInputChannel){
        true -> stack.add(LoopMarker(0, instruction, environment.getInt(), environment.getExecutionCaret()))
        false -> stack.add(LoopMarker(0, instruction, instruction.input.toInt(), environment.getExecutionCaret()))
    }


    private fun endLoopIteration(endInstruction: BracketInstruction){
        val (counter, startInstruction, value, caret) = stack.peek()

        if (startInstruction.shouldStopLooping(counter, endInstruction.getValue())){
            stack.pop()
        } else {
            stack.peek().increaseLoopCounter()
            environment.setExecutionCaret(caret)
            TODO("not implemented, time to loop it up, probably shange entire structure for reading instructions")

        }

    }
}