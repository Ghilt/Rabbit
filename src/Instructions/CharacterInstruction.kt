package Instructions

import OperatorType
import ExecutionTrack

class CharacterInstruction(operator: Char, inputCaret: Boolean, input: ArrayList<Char>, type: OperatorType ) : Instruction(operator, inputCaret, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if(operator.isDigit()){
            environment.setValue(Character.digit(operator,10))
        } else {
            environment.setValue(operator.toInt())
        }
    }

}