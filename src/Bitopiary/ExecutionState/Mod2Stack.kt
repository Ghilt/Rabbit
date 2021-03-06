package Bitopiary.ExecutionState


class CopyStack(private val environment : ExecutionTrack) {

    private var state = 0
        set(value){ field = value%2 }

    private var term = 0

    fun executeInstruction() {
        when (state) {
            0 -> term = environment.getInt()
            1 -> {
                environment.setValue(term)
                Bitopiary.Logger.l(environment, "CopyStack copying: $term")

            }
        }
        state++
    }

    fun executeInstruction(input: Int) {
        when (state) {
            0 -> term += input
            1 -> {
                environment.setValue(term)
                state++
                TODO("Undefined behaviour for copy")
            }
        }
        state++
    }
}