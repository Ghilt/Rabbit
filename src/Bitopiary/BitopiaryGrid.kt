package Bitopiary

import Bitopiary.ExecutionState.Caret
import Bitopiary.ExecutionState.ReadHead
import Extensions.boolToBinary

// https://discuss.kotlinlang.org/t/multi-dimensonal-arrays-are-a-pain-point-in-kotlin/561

class BitopiaryGrid(){

    private val grid = BitArrayGrid()

    fun getInt(readHead: ReadHead, caret: Caret): Int {

        val bitList = BooleanArray(readHead.size)
        grid.foldRange(caret, readHead, bitList) {
            accumulator, index, nextValue -> accumulator.apply { set(index, nextValue)}
        }
        return transformToInt(bitList)
    }

    fun getUnsignedInt(readHead: ReadHead, caret: Caret): Int {
        val bitList = BooleanArray(readHead.size)
        grid.foldRange(caret, readHead, bitList) {
            accumulator, index, nextValue -> accumulator.apply { set(index, nextValue)}
        }
        return Integer.parseInt(bitList.joinToString("", transform = boolToBinary), 2)
    }

    fun getChar(readHead: ReadHead, caret: Caret) = getUnsignedInt(readHead, caret).toChar()

    private fun transformToInt(bits: BooleanArray): Int {

        return if (bits.first()) { // Handle two's complement, kind of annoying to have to do it oneself
            val inverted = bits.map { b -> !b }.toMutableList()
            for ( i in inverted.size - 1 downTo 0) {
                inverted[i] = !inverted[i]
                if (inverted[i]) break
            }
            Integer.parseInt("-" + inverted.joinToString("", transform = boolToBinary), 2)
        } else {
            Integer.parseInt(bits.joinToString("", transform = boolToBinary), 2)
        }
    }

    fun setInt(readHead: ReadHead, caret: Caret, value: Int) {
        Logger.l("Set value to grid: $value / ${value.toChar()}")
        var mostSignificantFirst = Integer.toBinaryString(value).padStart(readHead.size, '0')
        mostSignificantFirst = mostSignificantFirst.substring(mostSignificantFirst.length - readHead.size until mostSignificantFirst.length)
        grid.setRange(caret, readHead, mostSignificantFirst)
    }

    fun debugPrint(width: Int = 128, height: Int = 10){
        for (y in 0..height){
            for (x in 0..width){
                print(if (grid[x, y]) "1 " else "_ " )
            }
            println()
        }
        println(".")
    }

    fun debugPrint(mem: Caret, exec: Caret, width: Int = 40, height: Int = 5) {
        for (y in 0..height){
            for (x in 0..width){
                val isMem = x == mem.x && y == mem.y
                val isExec = x == exec.x && y == exec.y

                var addon = " "
                if (isMem && isExec) {
                    addon = "b"
                } else if (isMem) {
                    addon = "m"
                } else if (isExec) {
                    addon = "x"
                }
                print(if (grid[x, y]) "1$addon" else "_$addon" )

            }
            println()
        }
        println(".")
    }

}