import java.nio.file.Files
import java.nio.file.Paths


class BitopiaryLexer(filePath: String) {

    private val commands = """'!"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~'"""

    val commandList = ArrayList<CommandBuilder>()

    init {
        val syntax = BitopiarySyntii()
        var builder = CommandBuilder(syntax)
        val stream = Files.newInputStream(Paths.get(filePath))
        stream.buffered().reader().use { reader ->
            val source = reader.readText()
            val chars = source.filter{x -> x in commands}

            for (value in chars) {
                val consumedChar = builder.tryConsumeAccordingToSyntax(value)

                if(!consumedChar){
                    println(builder.toString())
                    commandList.add(builder.createCommand())
                    builder = CommandBuilder(syntax)//TODO temptemp
                    builder.tryConsumeAccordingToSyntax(value)
                }
            }
            println(builder.toString())
            commandList.add(builder.createCommand())
        }
    }
}

class CommandBuilder(val syntax: BitopiarySyntii){ //dont use builder pattern

    var commandType :BitopiarySyntax? = null
    private val commands = ArrayList<Char>()
    var hasCommandModifier = false

    fun tryConsumeAccordingToSyntax(ch: Char) : Boolean{
        val currentCommandSyntax = syntax.all.filter { (operators) -> operators.contains(ch) }
        if ('.' != ch && currentCommandSyntax.size != 1) { // todo uggo
            throw Error("Syntax Error; Something went wrong")
        }
        if (commands.isEmpty()){
            return consumeOperator(ch, currentCommandSyntax[0])
        } else if (ch == syntax.commandModifier){
            return consumeCommandModifier()
        } else {

            if (canConsumeInput(commandType!!.standardInput, ch)){ // todo hehe nullthingamig
                commands.add(ch)
                return true
            }

        }

        return false
    }

    private fun canConsumeInput(standardInput: StandardInputType, ch: Char) : Boolean {
        if (commands.last().isDigit()){
            return ch.isDigit()
        }

        return when(standardInput) {
            StandardInputType.Source -> !hasCommandModifier && ch.isDigit()
            StandardInputType.Caret -> hasCommandModifier && (commands.size == 1 || commands.last().isDigit() && ch.isDigit())
            StandardInputType.IO -> false
        }
    }

    private fun consumeCommandModifier(): Boolean {
        if (hasCommandModifier || commands.size > 1) {
            throw Error("Syntax Error; double Command Modifier")
        } else {
            hasCommandModifier = true
            return true
        }
    }

    private fun consumeOperator(ch: Char, bitopiarySyntax: BitopiarySyntax): Boolean {
        if (ch == syntax.commandModifier) {
            throw Error("Syntax Error; lone Command Modifier")
        } else {
            commandType = bitopiarySyntax
            commands.add(ch)
            return true
        }
    }

    override fun toString() : String{
        var representation = commands.toCharArray().joinToString( separator = "", transform = { x -> x.toString() })
        return if (hasCommandModifier){
            representation.substring(0,1) + "." + representation.substring(1)
        } else  {
            representation
        }

    }

    fun createCommand() : CommandBuilder{ // todo
        return this
    }
}