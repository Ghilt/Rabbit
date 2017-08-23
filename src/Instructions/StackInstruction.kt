package Instructions

import OperatorType
import ExecutionTrack
import Extensions.*

class StackInstruction(operator: Char, inputCaret: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, inputCaret, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (inputCaret){
            environment.mod3Stacks[type]?.executeInstruction()
        } else {
            environment.mod3Stacks[type]?.executeInstruction(input.toInt())
        }
    }

}