import Instructions.AddInstruction
import Instructions.Instruction

class CommandBuilder(private val operator : Char, private val commandType : CommandType){

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
            else -> return if (canConsumeInput(commandType.standardInput, ch)){
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
        if (hasCommandModifier || inputToCommand.isNotEmpty()) {
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

    fun build(): Instruction {
        when (operator) {
            isBracket() -> "asda"
            else ->
        }
        return AddInstruction(hasCommandModifier, 100, StackType.Plus)
    }

}