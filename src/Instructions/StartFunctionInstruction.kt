package Instructions

import OperatorType
import ExecutionTrack
import Extensions.toInt

class StartFunctionInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (modifyInputChannel) {
            environment.startExecutionTrack(input.toInt())
        } else {
            environment.startExecutionTrack()
        }
    }
}