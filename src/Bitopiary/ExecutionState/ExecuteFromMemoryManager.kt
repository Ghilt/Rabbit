package Bitopiary.ExecutionState

import Bitopiary.CommandBuilder
import Bitopiary.OperatorType

open class ExecuteFromMemoryManager {

    var instruction: Char? = null
    var input: ArrayList<Char>? = null

    open fun executeFromMemory(environment: ExecutionTrack, c: ArrayList<Char>) {

        when {
            instruction == null -> instruction = c[0]
            input == null -> input = c
            else -> {
                val build = CommandBuilder(instruction ?: OperatorType.NO_OPERATION.toCharacter[0])
                build.setInputAsSource(ArrayList(input))
                instruction = null
                input = null
                build.build().execute(environment)
            }
        }
    }

}

class ExecuteFromMemoryManagerSimple {

    var instruction: Char? = null

    fun executeFromMemory(environment: ExecutionTrack, valChar: Char) {

        when (instruction) {
            null -> instruction = valChar
            else -> {
                val build = CommandBuilder(instruction ?: OperatorType.NO_OPERATION.toCharacter[0])
                build.setInputAsCaret()
                instruction = null
                build.build().execute(environment)
            }
        }
    }

}