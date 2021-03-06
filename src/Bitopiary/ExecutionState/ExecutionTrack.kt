package Bitopiary.ExecutionState

import Bitopiary.*
import Extensions.*

class ExecutionTrack(program: BitopiaryProgram,
                     private val grid: BitopiaryGrid,
                     private val executionDirection: OperatorType,
                     private val executionPointer: Caret,
                     startMemoryPos: Caret) : ConditionalStack.RestrictionTarget {

    var isTerminated = false
    data class Restriction(val restricted: Boolean = false, internal val source: OperatorType = OperatorType.NO_OPERATION)

    private var restrictInstructions = Restriction()
    private var caretCounter = 0
    private var carets = ArrayList<Caret>()
    private val readHead = ReadHead()
    private val userInputStream = UserInputStream()
    private val executeFromMemoryManagerSimple= ExecuteFromMemoryManagerSimple()
    private val executeFromMemoryManager = ExecuteFromMemoryManager()
    private val createExecutionTrackManager = NewExecutionTrackManager(program)
    var copyStack = CopyStack(this)
    var mod3Stacks = HashMap<OperatorType, Mod3Stack>()
    var loopStack = LoopStack(this)
    var conditionalStack = ConditionalStack(this)
    private val activeCaret: Caret
        get() = carets[caretCounter]

    init {
        carets.add(startMemoryPos)
        mod3Stacks.put(OperatorType.ADD, Mod3Stack(this, { x, y -> x + y }))
        mod3Stacks.put(OperatorType.SUBTRACT, Mod3Stack(this, { x, y -> x - y }))
        mod3Stacks.put(OperatorType.MULTIPLY, Mod3Stack(this, { x, y -> x * y }))
        mod3Stacks.put(OperatorType.DIVIDE, Mod3Stack(this, { x, y -> x / y }))
        mod3Stacks.put(OperatorType.MODULO, Mod3Stack(this, { x, y -> x % y }))
        mod3Stacks.put(OperatorType.AND, Mod3Stack(this, { x, y -> x and y }))
        mod3Stacks.put(OperatorType.OR, Mod3Stack(this, { x, y -> x or y }))
        mod3Stacks.put(OperatorType.XOR, Mod3Stack(this, { x, y -> x xor y }))
        mod3Stacks.put(OperatorType.FLIP, Mod3Stack(this,{ x, y -> x flip y}))
        mod3Stacks.put(OperatorType.SHIFT_LEFT, Mod3Stack(this,{ x, y -> x shl y}))
        mod3Stacks.put(OperatorType.SHIFT_RIGHT, Mod3Stack(this,{ x, y -> x shr y}))
    }


    fun execute() {
        Logger.l(grid, activeCaret, executionPointer)
        val (size, instruction) = CommandBuilder.readInstructionFromMemory(grid, readHead, executionPointer, executionDirection)
        executionPointer.moveCaret(executionDirection, readHead, size)
        if (restrictInstructions.restricted){
            instruction.restrictedExecute(this)
        } else {
            Logger.l(this, "Executing: ${instruction.toLogString()} ExecCaret: $executionPointer + MemCaret: $activeCaret")
            instruction.execute(this)
        }
    }

    fun getInt(caret: Caret = activeCaret): Int = grid.getInt(readHead, caret)

    fun setValue(value: Int, caret: Caret = activeCaret) = grid.setInt(readHead, caret, value)

    fun moveCaretDefault(direction: OperatorType) = activeCaret.moveCaret(direction, readHead)

    fun moveCaret(direction: OperatorType, distance: Int = getInt()) = activeCaret.moveCaret(direction, distance)

    fun modifyData(varyHow: (Int, Int) -> Int, varyBy: Int = getInt()) {
        grid.setInt(readHead, activeCaret, varyHow(getInt(), varyBy))
    }

    fun getExecutionCaretPosition(): Caret = Caret(executionPointer)

    fun setExecutionCaret(caret: Caret) {
        executionPointer.x = caret.x
        executionPointer.y = caret.y
    }

    fun print() {
        print(getInt())
    }

    fun printCharacter() {
        print(grid.getChar(readHead, activeCaret))
    }

    fun terminate(){
        isTerminated = true
    }

    override fun enableInstructions() {
        restrictInstructions = Restriction()
    }

    override fun restrictInstructions(source: OperatorType) {
        restrictInstructions = Restriction(true, source)
    }

    fun restrictedBy(): OperatorType = restrictInstructions.source

    fun isRestricted(): Boolean = restrictInstructions.restricted

    fun configureReadHead(dimensionDouble: Boolean = false, value: Int = getInt()) {
        if(dimensionDouble){
            readHead.configurationChange()
        } else {
            readHead.configurationChange(value)
        }
    }

    fun spawnNewCaret(amount: Int = getInt()) {
        for (nr in 1..amount) {
            carets.add(Caret(activeCaret))
        }
    }

    fun swapCaret(pos: Int = getInt(), cycle: Boolean = false) {
        caretCounter = if (cycle){
            (caretCounter + 1) % carets.size
        } else {
            pos % carets.size
        }
    }

    fun storeUserInputStream(input: String) {
        userInputStream.add(input)
    }

    fun loadInputToMemory() {
        setValue(userInputStream.get())
    }

    fun executeFromMemorySimple(instr: Char = grid.getChar(readHead, activeCaret)) {
        executeFromMemoryManagerSimple.executeFromMemory(this, instr)
    }

    fun executeFromMemory(fromInstruction: ArrayList<Char> = ArrayList<Char>().apply{ add(grid.getChar(readHead, activeCaret))}) {
        executeFromMemoryManager.executeFromMemory(this, fromInstruction)
    }

    fun queryForInformation(parameter: Char = grid.getChar(readHead, activeCaret)) {
        when (parameter){
            QueryParameter.version -> setValue(BitopiaryProgram.version)
            QueryParameter.readHeadWidth -> setValue(readHead.width)
            QueryParameter.readHeadHeight -> setValue(readHead.height)
            QueryParameter.readHeadSize -> setValue(readHead.size)
            QueryParameter.activeCaret -> setValue(caretCounter)
            QueryParameter.amountOfCarets -> setValue(carets.size)
            QueryParameter.caretX -> setValue(activeCaret.x)
            QueryParameter.caretY -> setValue(activeCaret.y)
            QueryParameter.maxVal -> setValue(-1 ushr 1)
            QueryParameter.minVal -> setValue(1 shl readHead.size-1)
            QueryParameter.ones -> setValue(-1)
            QueryParameter.mostSignificantOnes -> setValue(-1 ushr readHead.size/2)
            QueryParameter.leastSignificantOnes -> setValue(-1 shl readHead.size/2)
            QueryParameter.stack(parameter) -> setValue(mod3Stacks[parameter.toOperator()] !!.getTerm())

        }
    }

    fun startExecutionTrack(parameter: Int = getInt()) {
        createExecutionTrackManager.feedValue(parameter, carets.getElementModulo(parameter, carets.size))
    }

}



