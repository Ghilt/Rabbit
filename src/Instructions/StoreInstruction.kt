package Instructions

import OperatorType
import ExecutionTrack
import Extensions.*

class StoreInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType ) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (modifyInputChannel){
            //Do nothing, or set to same value
        } else {
            environment.setValue(input.toInt()) // TODO toInt need to handle characters appropriatly
        }
    }


}