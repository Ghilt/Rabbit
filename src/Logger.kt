object Logger {
    var logIt = false

    fun l(str: String){
        if(logIt){
            println(str)
        }
    }

    fun l(grid: BitopiaryGrid) {
        if (logIt) {
            println("Loaded grid with instructions:")
            grid.debugPrint()
        }
    }

    fun l(grid: BitopiaryGrid, caret: Caret, executionPointer: Caret) {
        if (logIt) {
            grid.debugPrint(caret, executionPointer)
        }
    }
}