package Instructions

import ExecutionTrack
import OperatorType

abstract class Instruction (operator: Char, internal val inputCaret: Boolean, internal val input: ArrayList<Char>, internal val type: OperatorType){
    abstract fun execute(environment: ExecutionTrack)
}