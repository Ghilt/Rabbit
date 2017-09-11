package Bitopiary

import Bitopiary.ExecutionState.Caret
import Bitopiary.ExecutionState.ReadHead
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import Extensions.*
import kotlin.collections.ArrayList


class BitopiaryLexer(filePath: String) {

    val program = BitopiaryProgram()

    init {
        val stream = Files.newInputStream(Paths.get(filePath))
        var source = ""
        stream.buffered().reader().use { reader ->
            source = reader.readText()
        }
        if (source.isNotEmpty()) {
            val tracks = source.split(BitopiarySyntax.parallelExecution)
            for ((index, track) in tracks.withIndex()) {
                logOriginalSource(track)
                loadSourceIntoProgramMemory(track, index)
            }
        }
    }

    private fun logOriginalSource(chars: String) {
        val commandList = ArrayList<CommandBuilder>()
        var builder = CommandBuilder(chars[0])
        for (value in chars.tail) {

            val didConsumeChar = builder.tryConsumeAccordingToSyntax(value)
            if (!didConsumeChar) {
                Logger.l(builder.toString())
                commandList.add(builder)
                builder = CommandBuilder(value)
            }
        }
        Logger.l(builder.toString())
        commandList.add(builder)
        if (!checkMatchingBrackets(commandList)) Logger.l("Syntax Warning; Brackets potentially mismatched")
    }

    private fun loadSourceIntoProgramMemory(chars: String, row: Int) {
        val defaultReadHead = ReadHead()
        program.loadInstructionTrackIntoMemory(chars, row)
        program.setExecutionTrack(Caret(0, row, readHead = defaultReadHead), Caret(0, row, readHead = defaultReadHead), OperatorType.MOVE_RIGHT)
    }

    private fun checkMatchingBrackets(track: ArrayList<CommandBuilder>) : Boolean {
        val stack = Stack<CommandBuilder>()

        for (c in track.filter { it.isBracket() }) {
            if (stack.isNotEmpty() && stack.peek().matchesBracket(c)) {
                stack.pop()
            } else {
                stack.push(c)
            }
        }
        return stack.isEmpty()
    }
}
