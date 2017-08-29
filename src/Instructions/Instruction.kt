package Instructions

import ExecutionTrack
import OperatorType

abstract class Instruction (val operator: Char, internal val modifyInputChannel: Boolean, internal val input: ArrayList<Char>, internal val type: OperatorType){
    abstract fun execute(environment: ExecutionTrack)
}