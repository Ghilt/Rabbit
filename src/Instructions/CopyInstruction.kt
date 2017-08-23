package Instructions

import OperatorType
import ExecutionTrack


class CopyInstruction(operator: Char, private val inputCaret: Boolean, private val input: ArrayList<Char>, private val type: OperatorType ) : Instruction {


    override fun execute(environment: ExecutionTrack) {
//            if (inputCaret){
//                environment.copyData(input)
//            } else {
//                environment.copyData(input)
//            }
    }


}