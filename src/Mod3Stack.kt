class Mod3Stack(private val environment :ExecutionTrack, private val operation :(Int, Int) -> Int) {

    private var state = 0
        set(value){ field = value%3 }

    private var term = 0

    fun executeInstruction() {
        when (state) {
            0 -> term += environment.getInt()
            1 -> term = operation(term, environment.getInt())
            2 -> {
                environment.setValue(term)
                term = 0
            }
        }
        state++
    }

    fun executeInstruction(input: Int) {
        when (state) {
            0 -> term += input
            1, 2 -> {
                term = operation(term, input)
                state = 0
            }
        }
        state++
    }
}