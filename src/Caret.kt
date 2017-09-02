class Caret(var x :Int = 0, var y :Int = 0) {
    constructor(caret: Caret) : this(caret.x, caret.y)
    constructor(x: Int, y: Int, readHead: ReadHead) : this(x * readHead.width, y * readHead.height)

    fun moveCaret(direction: OperatorType, readHead: ReadHead, steps: Int = 1) = when (direction){
        OperatorType.MOVE_UP, OperatorType.MOVE_DOWN -> moveCaret(direction, readHead.height * steps)
        OperatorType.MOVE_RIGHT, OperatorType.MOVE_LEFT -> moveCaret(direction, readHead.width * steps)
        else -> throw Error("Error; OperatorType Exception $direction")
    }

    fun moveCaret(direction: OperatorType, distance: Int) = when (direction){
        OperatorType.MOVE_UP -> y -= distance
        OperatorType.MOVE_RIGHT -> x += distance
        OperatorType.MOVE_DOWN -> y += distance
        OperatorType.MOVE_LEFT -> x -= distance
        else -> throw Error("Error; OperatorType Exception $direction")
    }

}