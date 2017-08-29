package Instructions

import OperatorType
import ExecutionTrack
import Extensions.*

class StackInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (modifyInputChannel){
            environment.mod3Stacks[type]?.executeInstruction(input.toInt())
        } else {
            environment.mod3Stacks[type]?.executeInstruction()
        }
    }

}