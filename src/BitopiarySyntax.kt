class BitopiarySyntii() {

    val commandModifier = '.'
    val all = ArrayList<BitopiarySyntax>()

    init{

        val moveInstructions = BitopiarySyntax("><_^", StandardInputType.Source, 1)
        val arithmeticInstructions = BitopiarySyntax("+*-/%", StandardInputType.Caret, 3)
        val bitInstructions = BitopiarySyntax("~@¨\"", StandardInputType.Caret, 3)
        val modifyBlockInstructions = BitopiarySyntax("|',", StandardInputType.Caret, 1)
        val copyInstruction = BitopiarySyntax("&", StandardInputType.Caret, 1)
        val readInstruction = BitopiarySyntax("=", StandardInputType.IO, 1)
        val printInstruction = BitopiarySyntax(":", StandardInputType.Caret, 1)
        val loopInstructions = BitopiarySyntax("()[]", StandardInputType.Caret, 1)
        val ifInstruction = BitopiarySyntax("{}", StandardInputType.Caret, 1)
        val environmentInstructions = BitopiarySyntax("£?", StandardInputType.Source, 1)
        val executeInstruction = BitopiarySyntax("!", StandardInputType.Caret, 3)
        val terminateInstruction = BitopiarySyntax("¤", StandardInputType.Caret, 1)
        val caretInstructions = BitopiarySyntax("\\$", StandardInputType.Caret, 1)
        val parallelInstructions = BitopiarySyntax("#", StandardInputType.Caret, 3)
        val charactersInstructions = BitopiarySyntax("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", StandardInputType.Caret, 3)
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
        return all.flatMap { (commands,_,_) -> commands.asIterable()}.contains(ch) //Don't optimize when you don't need to right/know what you are doing
    }
}

data class BitopiarySyntax(val operators: String, val standardInput: StandardInputType, val modulo: Int) {

}

enum class StandardInputType {
    Source, Caret, IO
}
