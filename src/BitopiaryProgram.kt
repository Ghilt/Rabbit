class BitopiaryProgram {

    val tracks = ArrayList<ExecutionTrack>()
    val grid = BitopiaryGrid(1000,1000)


    fun addExecutionTrack(instructions: ExecutionTrack) {
        tracks.add(instructions)
        instructions.bind(grid)
    }

    fun run(){
        while (tracks.isNotEmpty()) {
            tracks.forEach { it.execute()}
            tracks.removeAll(tracks.filter { it.isTerminated })
        }
    }
}