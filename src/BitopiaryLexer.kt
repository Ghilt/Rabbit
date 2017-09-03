import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList


class BitopiaryLexer(filePath: String) {

    val program = BitopiaryProgram()

    init {
        val stream = Files.newInputStream(Paths.get(filePath))
        var source = ""
        stream.buffered().reader().use { reader ->
            source = reader.readText()
        }
        val chars = source.filter{x -> x in BitopiarySyntax }
        if (chars.isNotEmpty()) {
            logOriginalSource(chars)
            loadSourceIntoProgramMemory(chars, program.grid)
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
        val splitTracks :ArrayList<ArrayList<CommandBuilder>> = commandList.splitTracks()
        splitTracks.filterNot { checkMatchingBrackets(it) }.forEach { Logger.l("Syntax Warning; Brackets potentially mismatched") }
    }

    private fun loadSourceIntoProgramMemory(chars: String, grid: BitopiaryGrid) {
        val instructions = ExecutionTrack()
        instructions.bind(grid)
        instructions.loadInstructionTrackIntoMemory(chars)
        program.addExecutionTrack(instructions)
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

private fun ArrayList<CommandBuilder>.splitTracks(): ArrayList<ArrayList<CommandBuilder>> {
    var splitCounter = 0
    val tracks = ArrayList<ArrayList<CommandBuilder>>()
    tracks.add(ArrayList())
    for (cb in this) {
        if(cb.isStartParallelExecution()){
            if (splitCounter == 0 && cb.hasCommandModifier) {
                tracks.add(ArrayList())
            } else {
                splitCounter = (splitCounter +1) % 3
                tracks.last().add(cb)
            }
        } else {
            tracks.last().add(cb)
        }
    }
    return tracks
}

//I wonder what the practise is where to keep extension things
val String.tail: String // Extension property
    get() = drop(1)

fun ArrayList<Char>.lastIsDigit() = lastOrNull()?.isDigit() == true // Extension function
