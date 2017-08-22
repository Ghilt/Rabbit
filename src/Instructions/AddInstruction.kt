package Instructions

import StackType
import Mod3Stack

class AddInstruction(private val inputCaret: Boolean, private val input: Int, private val stackTarget: StackType ) : Instruction {

    override fun execute(dataStacks: HashMap<StackType, Mod3Stack>) {
        if (inputCaret){
            dataStacks[stackTarget]?.executeInstruction()
        } else {
            dataStacks[stackTarget]?.executeInstruction(input)
        }
    }

}