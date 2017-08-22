import Instructions.Instruction

class ExecutionTrack {

    lateinit var grid :BitopiaryGrid
    var instructionCounter = 0
    var caretCounter = 0
    var carets = ArrayList<Caret>()
    val readHead = ReadHead()
    var mod3Stacks = HashMap<StackType, Mod3Stack>()
    val instructions = ArrayList<Instruction>()

    init {
        mod3Stacks.put(StackType.Plus, Mod3Stack(this, { x, y -> x + y}))
        mod3Stacks.put(StackType.Minus, Mod3Stack(this,{ x, y -> x - y}))
        mod3Stacks.put(StackType.Multiplication, Mod3Stack(this,{ x, y -> x * y}))
        mod3Stacks.put(StackType.Divide, Mod3Stack(this,{ x, y -> x / y}))
        mod3Stacks.put(StackType.BitAnd, Mod3Stack(this,{ x, y -> x and y}))
        mod3Stacks.put(StackType.BitOr, Mod3Stack(this,{ x, y -> x or y}))
        mod3Stacks.put(StackType.BitXor, Mod3Stack(this,{ x, y -> x xor y}))
        //mod3Stacks.put(StackType.BitFlip, Mod3Stack(this,{ x, y -> x flipIt y})) TODO
    }

    fun add(command: Instruction) {
        instructions.add(command)
    }

    fun bind(grid: BitopiaryGrid) {
        this.grid = grid
    }

    fun execute() {
        instructions[instructionCounter].execute(mod3Stacks)
        instructionCounter++
    }

    fun getInt(): Int = grid.getInt(readHead, carets[caretCounter])
    fun setInt(value: Int) = grid.setInt(readHead, carets[caretCounter], value)

}


enum class StackType {
    Plus, Minus, Multiplication, Divide, BitAnd, BitOr, BitXor, BitFlip
}