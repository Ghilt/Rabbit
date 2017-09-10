package Bitopiary.Instructions
import Bitopiary.OperatorType
import Bitopiary.ExecutionState.ExecutionTrack

class ReadInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {
    override fun execute(environment: ExecutionTrack) {
        if(!modifyInputChannel){
            val input = readLine() ?: return
            environment.storeUserInputStream(input)
        }
        environment.loadInputToMemory()
    }
}