package Instructions
import OperatorType
import ExecutionTrack

class BracketInstruction(operator: Char, inputCaret: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, inputCaret, input, type) {

    override fun execute(environment: ExecutionTrack) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun stopLooping(counter: Int, value: Int): Boolean{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
    fun getValue(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
}