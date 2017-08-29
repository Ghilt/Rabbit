package Instructions
import OperatorType
import ExecutionTrack

class ReadInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {
    override fun execute(environment: ExecutionTrack) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}