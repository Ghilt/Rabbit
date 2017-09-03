
class ExecutionTrack {

    var isTerminated = false
    private var restrictInstructions = false
    lateinit var grid :BitopiaryGrid
    private var caretCounter = 0
    private var carets = ArrayList<Caret>()
    private var executionPointer = Caret(0,0)
    private var executionDirection: OperatorType = OperatorType.MOVE_RIGHT
    private val readHead = ReadHead()
    var copyStack = CopyStack(this)
    var mod3Stacks = HashMap<OperatorType, Mod3Stack>()
    var loopStack = LoopStack(this)
    var conditionalStack = ConditionalStack(this)

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
        Logger.l(grid, carets[caretCounter], executionPointer)
        val (size, instruction) = CommandBuilder.readInstructionFromMemory(grid, readHead, executionPointer ,executionDirection)
        executionPointer.moveCaret(executionDirection, readHead, size)
//        Logger.l("Executing Instruction: ${instruction::class.qualifiedName}")
        if (restrictInstructions){
            instruction.restrictedExecute(this)
        } else {
            instruction.execute(this)
        }

    }

    fun getInt(caret: Caret = carets[caretCounter]): Int = grid.getInt(readHead, caret)

    fun setValue(value: Int, caret: Caret = carets[caretCounter]) = grid.setInt(readHead, caret, value)

    fun moveCaretDefault(direction: OperatorType) = carets[caretCounter].moveCaret(direction, readHead)

    fun moveCaret(direction: OperatorType, distance: Int = getInt()) = carets[caretCounter].moveCaret(direction, distance)

    fun modifyData(varyHow: (Int, Int) -> Int, varyBy: Int = getInt()) {
        grid.setInt(readHead, carets[caretCounter], varyHow(getInt(), varyBy))
    }

    fun getExecutionCaretPosition(): Caret = Caret(executionPointer)

    fun setExecutionCaret(caret: Caret) {
        executionPointer.x = caret.x
        executionPointer.y = caret.y
    }

    fun print() {
        println("printing : ${getInt()}")
    }

    fun loadInstructionTrackIntoMemory(chars: String) {
        for(index in 0 until chars.length){
            grid.setInt(readHead, Caret(index ,0, readHead), chars[index].toInt())
        }
        Logger.l(grid)
    }

    fun terminate(){
        isTerminated = true
    }

    fun enableInstructions() {
        restrictInstructions = false
    }

    fun restrictInstructions() {
        restrictInstructions = true
    }

    fun isRestricted(): Boolean = restrictInstructions

    fun configureReadHead(dimensionDouble: Boolean = false, value: Int = getInt()) {
        if(dimensionDouble){
            readHead.configurationChange()
        } else {
            readHead.configurationChange(value)
        }
    }

    fun spawnNewCaret(amount: Int = getInt()) {
        for (nr in 1..amount) {
            carets.add(Caret(carets[caretCounter]))
        }
    }

    fun swapCaret(pos: Int = getInt(), cycle: Boolean = false) {
        caretCounter = if (cycle){
            (caretCounter + 1) % carets.size
        } else {
            pos % carets.size
        }
    }

}