import java.nio.file.Files
import java.nio.file.Paths


class BitopiaryLexer(filePath: String) {

    private val commandList = ArrayList<CommandBuilder>()

    init {
        val stream = Files.newInputStream(Paths.get(filePath))
        stream.buffered().reader().use { reader ->
            val source = reader.readText()
            val chars = source.filter{x -> x in BitopiarySyntax }
            if (chars.isNotEmpty()) {
                readInCommands(chars)
            }
        }
    }

    private fun readInCommands(chars: String) {

        var builder = CommandBuilder(chars[0], BitopiarySyntax.getRelevantCommandType(chars[0]))
        for (value in chars.tail) {

            val didConsumeChar = builder.tryConsumeAccordingToSyntax(value)
            if (!didConsumeChar) {
                println(builder.toString())
                commandList.add(builder)
                builder = CommandBuilder(value, BitopiarySyntax.getRelevantCommandType(value))
            }
        }
        println(builder.toString())
        commandList.add(builder)
    }
}

class CommandBuilder(private val operator : Char,
                     private val commandType : CommandType){

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
            throw Error("Syntax Error; double Command Modifier")
        } else {
            hasCommandModifier = true
            return true
        }
    }

    override fun toString() : String{
        val representation = inputToCommand.toCharArray().joinToString( separator = "", transform = { x -> x.toString() })
        return  operator + (if (hasCommandModifier) "." else "") + representation
    }

}

//I wonder what the practise is where to keep extension things
val String.tail: String // Extension property
    get() = drop(1)

fun ArrayList<Char>.lastIsDigit() = lastOrNull()?.isDigit() == true // Extension function