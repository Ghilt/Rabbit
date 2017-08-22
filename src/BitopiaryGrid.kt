
// https://discuss.kotlinlang.org/t/multi-dimensonal-arrays-are-a-pain-point-in-kotlin/561

class BitopiaryGrid(val width: Int, val height: Int){
    private val grid = Array(height, {BooleanArray(width)})

    fun something() {
        grid[0][0]
    }

    fun getInt(readHead: ReadHead, caret: Caret): Int {
        for (x in caret.x..caret.x+readHead.width){
            for (y in caret.y..caret.y+readHead.height){
            }
        }
        return 0 //Filter and stuff instead of loops?

    }

    fun setInt(readHead: ReadHead, caret: Caret, value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}