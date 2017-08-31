package Instructions
import OperatorType
import ExecutionTrack
import Extensions.toInt

class BracketInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        environment.executeLoopInstruction(this)
    }

    fun shouldStopLooping(counter: Int, value: Int): Boolean{
        return when (type) {
            OperatorType.BEGIN_Q_LOOP, OperatorType.END_Q_LOOP -> counter == input.toInt()
            else -> TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    fun getValue(): Int = when (modifyInputChannel) {
        true -> input.toInt()
        false ->  TODO("not implemented, probably need to have executiontrack in this context")
    }
}