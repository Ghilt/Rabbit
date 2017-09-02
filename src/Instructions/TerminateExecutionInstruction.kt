package Instructions

import OperatorType
import ExecutionTrack


class TerminateExecutionInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) = environment.terminate()

}