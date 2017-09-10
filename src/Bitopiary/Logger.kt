package Bitopiary

import Bitopiary.ExecutionState.Caret
import Bitopiary.ExecutionState.ExecutionTrack

object Logger {

    private val tracks = ArrayList<ExecutionTrack>()

    var logIt = false
    var logGrid = false
    var logLevelExecutionTrack = true

    fun l(str: String){
        if(logIt && !logLevelExecutionTrack){
            println(str)
        }
    }

    fun l(track: ExecutionTrack, str: String){
        if(!tracks.contains(track)){
            tracks.add(track)
        }

        if(logIt){
            println("Track: ${tracks.indexOf(track)}, $str")
        }
    }

    fun l(grid: BitopiaryGrid) {
        if (logIt && logGrid) {
            println("Loaded grid with instructions:")
            grid.debugPrint()
        }
    }

    fun l(grid: BitopiaryGrid, caret: Caret, executionPointer: Caret) {
        if (logIt && logGrid) {
            grid.debugPrint(caret, executionPointer)
        }
    }
}