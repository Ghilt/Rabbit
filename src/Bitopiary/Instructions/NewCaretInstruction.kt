package Bitopiary.Instructions

import Bitopiary.ExecutionState.ExecutionTrack
import Bitopiary.OperatorType
import Extensions.*

class NewCaretInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        when {
            modifyInputChannel -> environment.spawnNewCaret()
            input.isEmpty() -> environment.spawnNewCaret(1)
            else -> environment.spawnNewCaret(input.toInt())
        }
    }

}

