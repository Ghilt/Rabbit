package Instructions

import OperatorType
import ExecutionTrack


class CopyInstruction(operator: Char, inputCaret: Boolean, input: ArrayList<Char>, type: OperatorType ) : Instruction(operator, inputCaret, input, type) {


    override fun execute(environment: ExecutionTrack) {
//            if (inputCaret){
//                environment.copyData(input)
//            } else {
//                environment.copyData(input)
//            }
    }


}