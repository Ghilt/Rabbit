package Bitopiary.Instructions
import Bitopiary.OperatorType
import Bitopiary.ExecutionState.ExecutionTrack
import Extensions.toInt

class BracketInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        environment.loopStack.executeLoopInstruction(this)
    }

    fun conditionMet(counter: Int, valueOfStart: Int, valueOfEnd: Int): Boolean{
        return when (type) {
            OperatorType.BEGIN_Q_LOOP -> counter == valueOfStart
            OperatorType.END_Q_LOOP -> counter == valueOfEnd
            OperatorType.BEGIN_LOOP -> valueOfStart == valueOfEnd
            OperatorType.END_LOOP -> valueOfStart != valueOfEnd
            OperatorType.BEGIN_IF -> valueOfStart == valueOfEnd
            OperatorType.END_IF -> valueOfStart != valueOfEnd
            else -> throw Error("An error has occurred in: BracketInstruction $type")
        }
    }

    fun getValue(valueAtCaret: Int): Int = when (modifyInputChannel) {
        true -> input.toInt()
        false ->  valueAtCaret
    }
}