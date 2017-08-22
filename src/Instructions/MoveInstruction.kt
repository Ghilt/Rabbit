package Instructions

import StackType
import Mod3Stack

class MoveInstruction(private val inputCaret: Boolean, private val input: Int?, private val direction: MoveType ) : Instruction {

    override fun execute(environment: ExecutionTrack) {
        if (inputCaret){
            environment.moveCaret(direction)
        } else if (input != null){
            environment.moveCaretDefault(direction)
        } else {
            environment.moveCaret(direction, input)
        }
    }

}