package Instructions

import ExecutionTrack
import OperatorType
import Extensions.*

class MoveInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {

        when {
            modifyInputChannel -> environment.moveCaret(type)
            input.isEmpty() -> environment.moveCaretDefault(type)
            else -> environment.moveCaret(type, input.toInt())
        }
    }

}

