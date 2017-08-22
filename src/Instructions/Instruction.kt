package Instructions

import ExecutionTrack

interface Instruction {
    fun execute(environment: ExecutionTrack)
}