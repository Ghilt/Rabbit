package Bitopiary.ExecutionState

import Bitopiary.Logger

class Mod3Stack(private val environment : ExecutionTrack, private val operation :(Int, Int) -> Int) {

    private var state = 0
        set(value){ field = value%3 }

    private var term = 0

    fun executeInstruction() {
        when (state) {
            0 -> {
                term += environment.getInt()
                Logger.l(environment, "Mod3Stack load: $term")
            }
            1 -> {
                val input = environment.getInt()
                val logVal = term
                term = operation(term, input)
                Logger.l(environment, "Mod3Stack operating: $logVal ? $input = $term")
            }
            2 -> {
                Logger.l(environment, "Mod3Stack output: $term")
                environment.setValue(term)
                term = 0
            }
        }
        state++
    }

    fun executeInstruction(input: Int) {
        when (state) {
            0 -> term += input
            1 -> term = operation(term, input)
            2 -> {
                Logger.l(environment, "Mod3Stack extending: $term")
                term = operation(term, input)
                state = 0
            }
        }
        state++
    }

    fun getTerm(): Int = term

}