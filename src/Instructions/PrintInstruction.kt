package Instructions
import OperatorType
import ExecutionTrack

class PrintInstruction(operator: Char, inputCaret: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, inputCaret, input, type) {
    override fun execute(environment: ExecutionTrack) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}