import Instructions.BracketInstruction
import Instructions.Instruction

class ExecutionTrack {

    var isTerminated = false
    lateinit var grid :BitopiaryGrid
    var caretCounter = 0
    var carets = ArrayList<Caret>()
    var executionPointer = Caret(0,0)
    var executionDirection: OperatorType = OperatorType.MOVE_RIGHT
    val readHead = ReadHead()
    var mod3Stacks = HashMap<OperatorType, Mod3Stack>()
    var copyStack = CopyStack(this)
    var loopStack = LoopStack(this)

    init {
        carets.add(Caret(0,0))
        mod3Stacks.put(OperatorType.ADD, Mod3Stack(this, { x, y -> x + y}))
        mod3Stacks.put(OperatorType.SUBTRACT, Mod3Stack(this,{ x, y -> x - y}))
        mod3Stacks.put(OperatorType.MULTIPLY, Mod3Stack(this,{ x, y -> x * y}))
        mod3Stacks.put(OperatorType.DIVIDE, Mod3Stack(this,{ x, y -> x / y}))
        mod3Stacks.put(OperatorType.MODULO, Mod3Stack(this,{ x, y -> x % y}))
        mod3Stacks.put(OperatorType.AND, Mod3Stack(this,{ x, y -> x and y}))
        mod3Stacks.put(OperatorType.OR, Mod3Stack(this,{ x, y -> x or y}))
        mod3Stacks.put(OperatorType.XOR, Mod3Stack(this,{ x, y -> x xor y}))
        //mod3Stacks.put(StackType.BitFlip, Mod3Stack(this,{ x, y -> x flipIt y})) TODO
    }


    fun bind(grid: BitopiaryGrid) {
        this.grid = grid
    }

    fun execute() {
        val (size, instruction) = CommandBuilder.readInstructionFromMemory(grid, readHead, executionPointer ,executionDirection)
        executionPointer.moveCaret(executionDirection, readHead, size)
        Logger.l("Executing Instruction: ${instruction::class.qualifiedName}")
        instruction.execute(this)

    }

    fun getInt(): Int = grid.getInt(readHead, carets[caretCounter])
    fun setValue(value: Int) = grid.setInt(readHead, carets[caretCounter], value)

    fun moveCaret(direction: OperatorType) = moveCaret(direction, getInt())

    fun moveCaretDefault(direction: OperatorType) = carets[caretCounter].moveCaret(direction, readHead)

    fun moveCaret(direction: OperatorType, distance: Int) = carets[caretCounter].moveCaret(direction, distance)

    fun modifyData(varyBy: (Int) -> Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getExecutionCaret(): Caret = executionPointer

    fun setExecutionCaret(caret: Caret) {
        executionPointer.x = caret.x
        executionPointer.y = caret.y
    }

    fun print() {
        println("printing : ${getInt()}")
    }

    fun executeLoopInstruction(bracketInstruction: BracketInstruction) = loopStack.executeLoopInstruction(bracketInstruction)

    fun loadInstructionTrackIntoMemory(chars: String) {
        for(index in 0 until chars.length){
            grid.setInt(readHead, Caret(index ,0, readHead), chars[index].toInt())
        }
        Logger.l(grid)
    }

    fun terminate(){
        isTerminated = true
    }


}


//enum class StackType {
//    Plus, Minus, Multiplication, Divide, BitAnd, BitOr, BitXor, BitFlip
//}
//
//enum class MoveType {
//    North, East, South, West
//}