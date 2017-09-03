import Instructions.Instruction

class CommandBuilder(private val operator : Char){

    companion object {
        data class InstructionInfo(val size: Int, val instruction: Instruction)
        fun readInstructionFromMemory(grid: BitopiaryGrid, readHead: ReadHead, caret: Caret, executionDirection: OperatorType): InstructionInfo {
            val position = Caret(caret)
            var size = 0
            val builder = CommandBuilder(grid.getChar(readHead, position))
            while (true){
                size++
                position.moveCaret(executionDirection, readHead)

                val didConsumeChar = builder.tryConsumeAccordingToSyntax(grid.getChar(readHead, position))
                if (!didConsumeChar) {
                    Logger.l("Read instruction: " + builder.toString())
                    break
                }
            }

            return InstructionInfo(size , builder.build())
        }
    }


    private val commandType : OperatorType = operator.toOperator()
    private val inputToCommand = ArrayList<Char>()
    var hasCommandModifier = false

    init {
        if (operator == BitopiarySyntax.commandModifier) {
            throw Error("Syntax Error; Command Modifier as command")
        }
    }


    fun tryConsumeAccordingToSyntax(ch: Char) : Boolean{
        return when (ch) {
            BitopiarySyntax.commandModifier -> consumeCommandModifier()
            else -> return if (canConsumeInput(commandType.input, ch)){
                inputToCommand.add(ch)
                true
            } else {
                false
            }
        }
    }

    private fun canConsumeInput(standardInput: StandardInputType, ch: Char) : Boolean {
        if (operator.isDigit() || inputToCommand.lastIsDigit()){
            return ch.isDigit()
        }

        return when(standardInput) {
            StandardInputType.Source -> !hasCommandModifier && ch.isDigit()
            StandardInputType.Caret -> hasCommandModifier && (inputToCommand.size == 0 || inputToCommand.lastIsDigit() && ch.isDigit())
            StandardInputType.IO -> false
        }
    }

    private fun consumeCommandModifier(): Boolean {
        if (hasCommandModifier || (inputToCommand.isNotEmpty() && commandType != OperatorType.CHARACTER )) {
            throw Error("Syntax Error; Double Command Modifier")
        } else {
            hasCommandModifier = true
            return true
        }
    }

    override fun toString() : String{
        val representation = inputToCommand.toCharArray().joinToString( separator = "", transform = { x -> x.toString() })
        return  operator + (if (hasCommandModifier) "." else "") + representation
    }

    fun isBracket(): Boolean = commandType.isBracket


    fun matchesBracket(bracket: CommandBuilder): Boolean  = when (bracket.operator) {
        '(' -> operator == ')'
        ')' -> operator == '('
        '[' -> operator == ']'
        ']' -> operator == '['
        '}' -> operator == '{'
        '{' -> operator == '}'
        else -> false
    }

    fun isStartParallelExecution(): Boolean {
        return  operator == BitopiarySyntax.parallelExecution
    }

    fun build(): Instruction = commandType.createInstruction(operator, hasCommandModifier, inputToCommand, commandType)

}



