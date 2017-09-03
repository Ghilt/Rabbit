package Instructions
import OperatorType
import ExecutionTrack
import Extensions.isomorphCompare
import Extensions.toInt

class ConditionalInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        environment.conditionalStack.executeConditionalInstruction(this)
    }

    fun getValue(valueAtCaret: Int): Int = when (modifyInputChannel) {
        true -> input.toInt()
        false ->  valueAtCaret
    }

    fun evaluateConditional(valueOfStart: Int, valueOfEnd: Int): Boolean {
        return isomorphCompare(OperatorType.BEGIN_IF, type, valueOfStart, valueOfEnd) {
            c1, c2 -> c1 == c2
        }
    }

}