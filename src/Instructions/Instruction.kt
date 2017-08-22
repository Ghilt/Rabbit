package Instructions

import Caret
import StackType
import Mod3Stack
import BitopiaryGrid

interface Instruction {
    fun execute(environment: ExecutionTrack)
}