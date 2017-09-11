package Bitopiary

import Bitopiary.ExecutionState.Caret
import Bitopiary.ExecutionState.ReadHead

class BitArrayGrid {

    private val grid = HashMap<Pair<Int, Int>, Boolean>()

    fun <T> foldRange(start: Caret, readHead: ReadHead, accumulator: T, bitAction: (T, Int, Boolean) -> T){
        val (startX, startY) = start
        val (width, height) = readHead

        var index = 0
        for (y in startY until startY+height) {
            for (x in startX until startX+width) {
                bitAction(accumulator, index ,grid[Pair(x,y)] ?: false)
                index++
            }
        }
    }

    fun setRange(start: Caret, readHead: ReadHead, value: String){
        val (startX, startY) = start
        val (width, height) = readHead

        var bitIndex = 0
        for (y in startY until startY+height) {
            for (x in startX until startX+width) {
                val p = Pair(x,y)
                grid[p] = value[bitIndex] == '1'
                bitIndex++
            }
        }
    }

    operator fun get(x: Int, y: Int) = grid[Pair(x, y)] ?: false

}