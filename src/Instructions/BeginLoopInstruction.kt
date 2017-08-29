package Instructions

import OperatorType
import ExecutionTrack

class BeginLoopInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (modifyInputChannel){

        } else {

        }
    }

}