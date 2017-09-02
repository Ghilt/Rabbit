package Instructions
import OperatorType
import ExecutionTrack
import Extensions.toInt

class BracketInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        environment.executeLoopInstruction(this)
    }

    fun conditionMet(counter: Int, valueOfStart: Int, valueOfEnd: Int): Boolean{
        return when (type) {
            OperatorType.BEGIN_Q_LOOP -> counter == valueOfStart
            OperatorType.END_Q_LOOP -> counter == valueOfEnd
            OperatorType.BEGIN_LOOP -> valueOfStart == valueOfEnd
            OperatorType.END_LOOP -> valueOfStart != valueOfEnd
            else -> TODO("not implemented, If statment prolly")
        }
    }

    fun getValue(valueAtCaret: Int): Int = when (modifyInputChannel) {
        true -> input.toInt()
        false ->  valueAtCaret
    }
}