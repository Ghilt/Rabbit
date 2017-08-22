package Instructions

import ExecutionTrack
import OperatorType

class MoveInstruction(private val inputCaret: Boolean, private val input: Int, private val direction: OperatorType) : Instruction {

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