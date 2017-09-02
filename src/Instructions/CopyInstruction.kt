package Instructions

import OperatorType
import ExecutionTrack
import Extensions.toInt


class CopyInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType ) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
            if (modifyInputChannel){
                environment.copyStack.executeInstruction(input.toInt())
            } else {
                environment.copyStack.executeInstruction()
            }
    }


}