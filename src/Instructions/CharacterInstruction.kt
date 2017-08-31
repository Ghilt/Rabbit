package Instructions

import OperatorType
import ExecutionTrack
import Extensions.toInt

class CharacterInstruction(operator: Char, inputCaret: Boolean, input: ArrayList<Char>, type: OperatorType ) : Instruction(operator, inputCaret, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if(operator.isDigit()){
            var entire = ArrayList(input)
            entire.add(0, operator)
            environment.setValue(entire.toInt())
        } else {
            environment.setValue(operator.toInt())
        }
    }

}