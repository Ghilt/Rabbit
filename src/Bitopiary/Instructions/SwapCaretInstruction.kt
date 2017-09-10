package Bitopiary.Instructions

import Bitopiary.ExecutionState.ExecutionTrack
import Bitopiary.OperatorType
import Extensions.*

class SwapCaretInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        when {
            modifyInputChannel -> environment.swapCaret()
            input.isEmpty() -> environment.swapCaret( cycle = true)
            else -> environment.swapCaret(input.toInt())
        }
    }

}