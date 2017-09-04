import Extensions.toInt

class ExecuteFromMemoryManager {

    var instruction: Char? = null
    var input: ArrayList<Char>? = null

    fun executeFromMemory(environment: ExecutionTrack, c: ArrayList<Char>) {

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