
// https://discuss.kotlinlang.org/t/multi-dimensonal-arrays-are-a-pain-point-in-kotlin/561

class BitopiaryGrid(val width: Int, val height: Int){
    private val grid = Array(height, {BooleanArray(width)})

    fun getInt(readHead: ReadHead, caret: Caret): Int {
        //this isn't readable kotlin? but how do you want it!?
        var filterHeight = { index: Int, _: BooleanArray ->  index in caret.y until caret.y + readHead.height}
        var filterWidth = { index: Int, _: Boolean ->  index in caret.x until caret.x + readHead.width}

        val bitList = grid.filterIndexed(filterHeight).flatMap { array -> array.filterIndexed(filterWidth).toList()}
        return transformToInt(bitList)
    }

    private fun transformToInt(bits: Iterable<Boolean>): Int {
        val boolToBinary = { b: Boolean -> if (b) "1" else "0" }
        return if (bits.first()) { // Handle two's complement, I wonder if this really has to be done
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
        val leastSignificantFirst = Integer.toBinaryString(value).reversed().padEnd(readHead.size, '0')
        var bitIndex = 0
        for (y in caret.y + readHead.height-1 downTo caret.y){
            for (x in caret.x + readHead.width-1 downTo caret.x){
                grid[y][x] = leastSignificantFirst[bitIndex] == '1'
                bitIndex++
                if(bitIndex >= leastSignificantFirst.length){
                    return
                }
            }
        }
    }

    fun debugPrint(width: Int, height: Int){
        for (y in 0..height){
            for (x in 0..width){
                print(if (grid[y][x]) "1 " else "_ " )
            }
            println()
        }
        println(".")
    }
}