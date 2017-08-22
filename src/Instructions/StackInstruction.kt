package Instructions

import OperatorType
import ExecutionTrack

class StackInstruction(private val inputCaret: Boolean, private val input: Int, private val stackTarget: OperatorType ) : Instruction {

    override fun execute(environment: ExecutionTrack) {
        if (inputCaret){
            environment.mod3Stacks[stackTarget]?.executeInstruction()
        } else {
            environment.mod3Stacks[stackTarget]?.executeInstruction(input)
        }
    }

}