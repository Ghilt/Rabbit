package Bitopiary.ExecutionState
import Bitopiary.Logger
import Bitopiary.Instructions.*
import java.util.*
import Extensions.*


class LoopStack(private val environment : ExecutionTrack) {

    data class LoopMarker(var counter: Int, val instruction: BracketInstruction, var value: Int, val startLoopCaret: Caret, val restrictedLoop: Boolean) {

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

        /** if the outer loop is restricted then this loop should have no effect*/
        val shouldRestrict = instruction.doZeroIterations(relevantValue) || stack.parentIsRestricted()
        if (shouldRestrict){
            environment.restrictInstructions(instruction.type)
        }
        stack.add(LoopMarker(1, instruction, relevantValue, environment.getExecutionCaretPosition(), shouldRestrict))
    }

    private fun endLoopIteration(endInstruction: BracketInstruction){
        val (counter, startInstruction, valueOfStart, caret) = stack.peek()

        val valueOfEnd = endInstruction.getValue(environment.getInt())
        if (startInstruction.conditionMet(counter, valueOfStart, valueOfEnd)){
            finishLoop()
        } else {
            stack.peek().increaseLoopCounter()
            environment.setExecutionCaret(caret)
        }
    }

    private fun finishLoop() {
        val marker = stack.pop()

        /** Diagram for restriction logic
         *
         *
         *   Parent restricted   │   True                    False
         *   This restricted     ├──────────────────────────────────────
         *      True             │   Don't enable            Enable
         *      False            │   Don't enable            Does not matter, do nothing
         */

        if (stack.parentIsRestricted()) {
            Logger.l(environment, "End of restricted loop, by self(${marker.restrictedLoop}), by parent(${stack.parentIsRestricted()})")
        } else {
            environment.enableInstructions()
            Logger.l(environment, "Exiting Loop at ${marker.counter} iterations")
        }
    }
}

