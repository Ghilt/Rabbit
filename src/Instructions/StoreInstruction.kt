package Instructions

import OperatorType
import ExecutionTrack
import Extensions.*

class StoreInstruction(operator: Char, inputCaret: Boolean, input: ArrayList<Char>, type: OperatorType ) : Instruction(operator, inputCaret, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if (inputCaret){
            //Do nothing, or set to same value
        } else {
            environment.setInt(input.toInt()) // TODO toInt need to handle characters appropriatly
        }
    }


}