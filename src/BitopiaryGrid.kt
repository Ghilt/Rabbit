
// https://discuss.kotlinlang.org/t/multi-dimensonal-arrays-are-a-pain-point-in-kotlin/561

class BitopiaryGrid(val width: Int, val height: Int){
    private val grid = Array(height, {BooleanArray(width)})

    fun something() {
        grid[0][0]
    }

    fun getInt(readHead: ReadHead, caret: Caret): Int {
        //this isn't readable kotlin? but how do you want it!?
        val bitList = grid.filterIndexed { index, _ ->  index in caret.y until caret.y + readHead.height}.flatMap{ array -> array.filterIndexed{ index, _ ->  index in caret.x until caret.x + readHead.width}.toList()}
        return transformToInt(bitList)

    }

    private fun transformToInt(bits: List<Boolean>) = Integer.parseInt(bits.joinToString("") {b -> if (b) "1" else "0" }, 2)

    fun setInt(readHead: ReadHead, caret: Caret, value: Int) {
        val leastSignificantFirst = Integer.toBinaryString(value).reversed()
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