package Instructions

import OperatorType
import ExecutionTrack
import Extensions.*

class StoreInstruction(operator: Char, private val inputCaret: Boolean, private val input: ArrayList<Char>, private val stackTarget: OperatorType ) : Instruction {

    override fun execute(environment: ExecutionTrack) {
        if (inputCaret){
            //Do nothing, or set to same value
        } else {
            environment.setInt(input.toInt()) // TODO toInt need to handle characters appropriatly
        }
    }


}