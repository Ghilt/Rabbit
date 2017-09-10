package Bitopiary.Instructions
import Bitopiary.OperatorType
import Bitopiary.ExecutionState.ExecutionTrack

class PrintInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (modifyInputChannel) {
            environment.printCharacter()
        } else {
            environment.print()
        }
    }
}