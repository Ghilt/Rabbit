object Logger {
    var logIt = true

    fun l(str: String){
        if(logIt){
            println(str)
        }
    }

    fun l(grid: BitopiaryGrid) {
        if (logIt) {
//            println("Loaded grid with instructions:")
//            grid.debugPrint()
        }
    }
}