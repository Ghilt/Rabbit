package Bitopiary.ExecutionState

import Extensions.toInt

class UserInputStream{

    private val data: ArrayList<Char> = ArrayList()

    fun add(input: String) {
        for( c in input){
            data.add(c)
        }
    }

    fun get(): Int {
        val listOfDigits = data.fold(ArrayList<Char>()) {
            accumulator, c -> if (c.isDigit()) accumulator.apply{add(c)} else accumulator }

        return if (listOfDigits.isEmpty() && data.isNotEmpty()){
            data.removeAt(0).toInt()
        } else {
            listOfDigits.toInt()
        }
    }
}