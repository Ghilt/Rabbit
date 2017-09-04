package Instructions

import OperatorType
import ExecutionTrack

class ExecuteInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (modifyInputChannel) {
            environment.executeFromMemory(input)
        } else {
            environment.executeFromMemory()
        }
    }
}