package Bitopiary

import Bitopiary.ExecutionState.Caret
import Bitopiary.ExecutionState.ExecutionTrack
import Bitopiary.ExecutionState.ReadHead

class BitopiaryProgram {

    companion object {
        var version = 1
    }

    private val tracks = ArrayList<ExecutionTrack>()
    private val tracksToBeAdded = ArrayList<ExecutionTrack>()
    val grid = BitopiaryGrid(1000, 1000)


    fun setExecutionTrack(startPoint: Caret, startMemoryPos: Caret, direction: OperatorType) {
        tracks.add(ExecutionTrack(this, grid, direction, startPoint, startMemoryPos))
    }

    fun addExecutionTrack(startPoint: Caret, startMemoryPos: Caret, direction: OperatorType) {
        Logger.l("Add new executionTrack: execStart: $startPoint memStart: $startMemoryPos")
        tracksToBeAdded.add(ExecutionTrack(this, grid, direction, startPoint, startMemoryPos))
    }

    fun run(){
        while (tracks.isNotEmpty()) {
            tracks.forEach { it.execute()}
            tracks.removeAll(tracks.filter { it.isTerminated })
            tracksToBeAdded.forEach{tracks.add(it)}
            tracksToBeAdded.clear()
        }
    }

    fun loadInstructionTrackIntoMemory(chars: String, row: Int) {
        val readHead = ReadHead()
        for(index in 0 until chars.length){
            grid.setInt(readHead, Caret(index, row, readHead), chars[index].toInt())
        }
        Logger.l(grid)
    }

}