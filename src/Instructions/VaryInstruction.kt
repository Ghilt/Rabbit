package Instructions

import OperatorType
import ExecutionTrack
import Extensions.toInt

class VaryInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    private val variation = if (input.isEmpty()) 1 else input.toInt()
    private val varyByUnknown = if (type == OperatorType.INCREASE) { x: Int, y: Int -> x + y } else { x: Int, y: Int -> x - y }

    override fun execute(environment: ExecutionTrack) = if (modifyInputChannel) environment.modifyData(varyByUnknown) else environment.modifyData(varyByUnknown, variation)

}