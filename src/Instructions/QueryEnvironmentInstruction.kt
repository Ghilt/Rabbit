package Instructions

import OperatorType
import ExecutionTrack

class QueryEnvironmentInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (modifyInputChannel) {
            environment.queryForInformation(input.first())
        } else {
            environment.queryForInformation()
        }
    }
}