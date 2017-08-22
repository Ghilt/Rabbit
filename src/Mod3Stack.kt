class Mod3Stack(val environment :ExecutionTrack, val operation :(Int, Int) -> Int) {

    private var state = 0
        set(value){ field = value%3 }

    private var term = 0

    fun executeInstruction() {
        state++
        when (state) {
            0 -> term = environment.getInt()
            1 -> term = operation(term, environment.getInt())
            2 -> {
                environment.setInt(term)
                term = 0
            }
        }
    }

    fun executeInstruction(input: Int) {
        state++

    }
}