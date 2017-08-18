object BitopiarySyntax {

    val commandModifier = '.'
    val all = ArrayList<CommandType>()

    init{

        val moveInstructions = CommandType("><_^", StandardInputType.Source, 1)
        val arithmeticInstructions = CommandType("+*-/%", StandardInputType.Caret, 3)
        val bitInstructions = CommandType("~@¨\"", StandardInputType.Caret, 3)
        val modifyBlockInstructions = CommandType("|',", StandardInputType.Caret, 1)
        val copyInstruction = CommandType("&", StandardInputType.Caret, 1)
        val readInstruction = CommandType("=", StandardInputType.IO, 1)
        val printInstruction = CommandType(":", StandardInputType.Caret, 1)
        val loopInstructions = CommandType("()[]", StandardInputType.Caret, 1, true)
        val ifInstruction = CommandType("{}", StandardInputType.Caret, 1, true)
        val environmentInstructions = CommandType("£?", StandardInputType.Source, 1)
        val executeInstruction = CommandType("!", StandardInputType.Caret, 3)
        val terminateInstruction = CommandType("¤", StandardInputType.Caret, 1)
        val caretInstructions = CommandType("\\$", StandardInputType.Caret, 1)
        val parallelInstructions = CommandType("#", StandardInputType.Caret, 3)
        val charactersInstructions = CommandType("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", StandardInputType.Caret, 3)
        all.add(moveInstructions)
        all.add(arithmeticInstructions)
        all.add(bitInstructions)
        all.add(modifyBlockInstructions)
        all.add(copyInstruction)
        all.add(readInstruction)
        all.add(printInstruction)
        all.add(loopInstructions)
        all.add(ifInstruction)
        all.add(environmentInstructions)
        all.add(executeInstruction)
        all.add(terminateInstruction)
        all.add(caretInstructions)
        all.add(parallelInstructions)
        all.add(charactersInstructions)

    }

    operator fun contains(ch :Char) : Boolean{
        return ch == commandModifier || all.flatMap { (commands,_,_) -> commands.asIterable()}.contains(ch) //Don't optimize when you don't need to right/know what you are doing
    }

    fun getRelevantCommandType(ch: Char): CommandType {
        val narrowedDown = all.filter { (operators) -> operators.contains(ch) }
        if(narrowedDown.size != 1){
            throw Error("Error; ${narrowedDown.size} commandTypes matched character")
        } else {
            return narrowedDown[0]
        }

    }
}

data class CommandType(val operators: String, val standardInput: StandardInputType, val modulo: Int, val isBracket :Boolean = false)

enum class StandardInputType {
    Source, Caret, IO
}
