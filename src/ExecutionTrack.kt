import Instructions.Instruction

class ExecutionTrack {

    lateinit var grid :BitopiaryGrid
    var instructionCounter = 0
    var caretCounter = 0
    var carets = ArrayList<Caret>()
    val readHead = ReadHead()
    var mod3Stacks = HashMap<OperatorType, Mod3Stack>()
    var copyStack = CopyStack(this)
    var loopStack = LoopStack(this)
    val instructions = ArrayList<Instruction>()

    init {
        mod3Stacks.put(OperatorType.ADD, Mod3Stack(this, { x, y -> x + y}))
        mod3Stacks.put(OperatorType.SUBTRACT, Mod3Stack(this,{ x, y -> x - y}))
        mod3Stacks.put(OperatorType.MULTIPLY, Mod3Stack(this,{ x, y -> x * y}))
        mod3Stacks.put(OperatorType.DIVIDE, Mod3Stack(this,{ x, y -> x / y}))
        mod3Stacks.put(OperatorType.AND, Mod3Stack(this,{ x, y -> x and y}))
        mod3Stacks.put(OperatorType.OR, Mod3Stack(this,{ x, y -> x or y}))
        mod3Stacks.put(OperatorType.XOR, Mod3Stack(this,{ x, y -> x xor y}))
        //mod3Stacks.put(StackType.BitFlip, Mod3Stack(this,{ x, y -> x flipIt y})) TODO
    }

    fun add(command: Instruction) {
        instructions.add(command)
    }

    fun bind(grid: BitopiaryGrid) {
        this.grid = grid
    }

    fun execute() {
        if(instructionCounter < instructions.size){
            var instr = instructions[instructionCounter]
            instr.execute(this)
            instructionCounter++
        }
    }

    fun getInt(): Int = grid.getInt(readHead, carets[caretCounter])
    fun setInt(value: Int) = grid.setInt(readHead, carets[caretCounter], value)

    fun moveCaret(direction: OperatorType) = moveCaret(direction, getInt())

    fun moveCaretDefault(direction: OperatorType) = when (direction){
        OperatorType.MOVE_UP, OperatorType.MOVE_DOWN -> moveCaret(direction, readHead.height)
        OperatorType.MOVE_RIGHT, OperatorType.MOVE_LEFT -> moveCaret(direction, readHead.width)
        else -> throw Error("Error; OperatorType Exception $direction")
    }

    fun moveCaret(direction: OperatorType, distance: Int) = when (direction){
        OperatorType.MOVE_UP -> carets[caretCounter].y += distance
        OperatorType.MOVE_RIGHT -> carets[caretCounter].x += distance
        OperatorType.MOVE_DOWN -> carets[caretCounter].y -= distance
        OperatorType.MOVE_LEFT -> carets[caretCounter].x -= distance
        else -> throw Error("Error; OperatorType Exception $direction")
    }

    fun modifyData(varyBy: (Int) -> Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getExecutionCaret(): Caret {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setExecutionCaret(caret: Caret) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


//enum class StackType {
//    Plus, Minus, Multiplication, Divide, BitAnd, BitOr, BitXor, BitFlip
//}
//
//enum class MoveType {
//    North, East, South, West
//}