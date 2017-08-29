package Instructions

import OperatorType
import ExecutionTrack
import Extensions.toInt

class VaryInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    private val variation = if (input.isEmpty()) 1 else input.toInt()
    private val varyBy = if (type == OperatorType.INCREASE) { x: Int -> x + variation } else { x: Int -> x - variation }

    override fun execute(environment: ExecutionTrack) = environment.modifyData(varyBy)

}