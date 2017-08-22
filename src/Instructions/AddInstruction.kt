package Instructions

import StackType
import Mod3Stack

class AddInstruction(private val inputCaret: Boolean, private val input: Int, private val stackTarget: StackType ) : Instruction {

    override fun execute(environment: ExecutionTrack) {
        if (inputCaret){
            environment.dataStacks[stackTarget]?.executeInstruction()
        } else {
            environment.dataStacks[stackTarget]?.executeInstruction(input)
        }
    }

}