package Bitopiary.ExecutionState

import Bitopiary.*
import Extensions.*

class ExecutionTrack(private val program: BitopiaryProgram,
                     private val grid: BitopiaryGrid,
                     private val executionDirection: OperatorType,
                     private val executionPointer: Caret,
                     startMemoryPos: Caret) {

    var isTerminated = false
    private var restrictInstructions = false
    private var caretCounter = 0
    private var carets = ArrayList<Caret>()
    private val readHead = ReadHead()
    private val userInputStream = UserInputStream()
    private val executeFromMemoryManager = ExecuteFromMemoryManager() // TODO make generic base
    private val createExecutionTrackManager = NewExecutionTrackManager(program) // TODO make generic base
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
        //mod3Stacks.put(StackType.BitFlip, ExecutionState.Mod3Stack(this,{ x, y -> x flipIt y})) TODO
    }

    fun execute() {
        Logger.l(grid, activeCaret, executionPointer)
        val (size, instruction) = CommandBuilder.readInstructionFromMemory(grid, readHead, executionPointer, executionDirection)
        executionPointer.moveCaret(executionDirection, readHead, size)
        if (restrictInstructions){
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

    fun executeFromMemory(fromInstruction: ArrayList<Char> = ArrayList<Char>().apply{ add(grid.getChar(readHead, activeCaret))}) {
        executeFromMemoryManager.executeFromMemory(this, fromInstruction)
    }

    fun queryForInformation(parameter: Char = grid.getChar(readHead, activeCaret)) {
        when (parameter){
            QueryParameter.version -> setValue(BitopiaryProgram.version.toInt())
            QueryParameter.readHeadWidth -> setValue(readHead.width)
            QueryParameter.readHeadHeight -> setValue(readHead.height)
            QueryParameter.activeCaret -> setValue(caretCounter)
            QueryParameter.caretX -> setValue(activeCaret.x)
            QueryParameter.caretY -> setValue(activeCaret.y)
            QueryParameter.stack(parameter) -> setValue(mod3Stacks[parameter.toOperator()] !!.getTerm())

        }
    }

    fun startExecutionTrack(parameter: Int = getInt()) {
        createExecutionTrackManager.feedValue(parameter, carets.getElementModulo(parameter, carets.size))
    }

}


