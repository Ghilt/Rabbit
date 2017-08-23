package Instructions

import ExecutionTrack
import OperatorType
import Extensions.*

class MoveInstruction(operator: Char, inputCaret: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, inputCaret, input, type) {

    override fun execute(environment: ExecutionTrack) {

        when {
            inputCaret -> environment.moveCaret(type)
            input.isEmpty() -> environment.moveCaretDefault(type)
            else -> environment.moveCaret(type, input.toInt())
        }
    }

}

