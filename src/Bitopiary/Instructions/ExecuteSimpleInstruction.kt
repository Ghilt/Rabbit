package Bitopiary.Instructions

import Bitopiary.ExecutionState.ExecutionTrack
import Bitopiary.OperatorType

class ExecuteSimpleInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (modifyInputChannel) {
            environment.executeFromMemorySimple(input[0])
        } else {
            environment.executeFromMemorySimple()
        }
    }
}