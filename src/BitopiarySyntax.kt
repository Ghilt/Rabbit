import Instructions.*
import Extensions.*

object BitopiarySyntax {

    val parallelExecution = '#'
    val commandModifier = '.'
//    private val all = ArrayList<CommandType>()
//
//    init{
//
//        val moveInstructions = CommandType("><_^", StandardInputType.Source, 1)
//        val arithmeticInstructions = CommandType("+*-/%", StandardInputType.Caret, 3, usesStack = true)
//        val bitInstructions = CommandType("~@¨\"", StandardInputType.Caret, 3, usesStack = true)
//        val modifyBlockInstructions = CommandType("|',", StandardInputType.Caret, 1)
//        val copyInstruction = CommandType("&", StandardInputType.Caret, 1)
//        val readInstruction = CommandType("=", StandardInputType.IO, 1)
//        val printInstruction = CommandType(":", StandardInputType.Caret, 1)
//        val loopInstructions = CommandType("()[]", StandardInputType.Caret, 1, true)
//        val ifInstruction = CommandType("{}", StandardInputType.Caret, 1, true)
//        val environmentInstructions = CommandType("£?", StandardInputType.Source, 1)
//        val executeInstruction = CommandType("!", StandardInputType.Caret, 3)
//        val terminateInstruction = CommandType("¤", StandardInputType.Caret, 1)
//        val caretInstructions = CommandType("\\$", StandardInputType.Caret, 1)
//        val parallelInstructions = CommandType("$parallelExecution", StandardInputType.Caret, 3)
//        val charactersInstructions = CommandType("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", StandardInputType.Caret, 1)
//        all.add(moveInstructions)
//        all.add(arithmeticInstructions)
//        all.add(bitInstructions)
//        all.add(modifyBlockInstructions)
//        all.add(copyInstruction)
//        all.add(readInstruction)
//        all.add(printInstruction)
//        all.add(loopInstructions)
//        all.add(ifInstruction)
//        all.add(environmentInstructions)
//        all.add(executeInstruction)
//        all.add(terminateInstruction)
//        all.add(caretInstructions)
//        all.add(parallelInstructions)
//        all.add(charactersInstructions)
//
//    }

    operator fun contains(ch :Char) : Boolean{
        return OperatorType.values().contains(ch.toOperator()) //Don't optimize when you don't need to right/know what you are doing
    }
}

fun Char.toOperator(): OperatorType = when(this) {
    '>' -> OperatorType.MOVE_RIGHT
    '<' -> OperatorType.MOVE_LEFT
    '^' -> OperatorType.MOVE_UP
    '_' -> OperatorType.MOVE_DOWN
    '+' -> OperatorType.ADD
    '-' -> OperatorType.SUBTRACT
    '/' -> OperatorType.DIVIDE
    '*' -> OperatorType.MULTIPLY
    '%' -> OperatorType.MODULO
    '~' -> OperatorType.FLIP
    '&' -> OperatorType.AND
    '|' -> OperatorType.OR
    '¨' -> OperatorType.XOR
    '"' -> OperatorType.STORE
    '@' -> OperatorType.COPY
    '\'' -> OperatorType.INCREASE
    ',' -> OperatorType.DECREASE
    '$' -> OperatorType.NEW_CARET
    '\\' -> OperatorType.CHANGE_CARET
    '{' -> OperatorType.BEGIN_IF
    '[' -> OperatorType.BEGIN_LOOP
    '(' -> OperatorType.BEGIN_Q_LOOP
    '}' -> OperatorType.END_IF
    ']' -> OperatorType.END_LOOP
    ')' -> OperatorType.END_Q_LOOP
    '!' -> OperatorType.EXECUTE
    '¤' -> OperatorType.EXIT_EXECUTION
    '?' -> OperatorType.QUERY_ENVIRONMENT
    '#' -> OperatorType.START_FUNCTION
    '.' -> OperatorType.MODIFIER
    '0','1','2','3','4','5','6','7','8','9',
    'A','B','C','D','E','F','G','H','I','J',
    'K','L','M','N','O','P','Q','R','S','T',
    'U','V','W','X','Y','Z','a','b','c','d',
    'e','f','g','h','i','j','k','l','m','n',
    'o','p','q','r','s','t','u','v','w','x',
    'y','z' -> OperatorType.CHARACTER
    else -> throw Error("Syntax Error: character is not allowed ${this}")
}

data class CommandType(val operators: String, val standardInput: StandardInputType, val modulo: Int, val isBracket: Boolean = false, val usesStack: Boolean = false)

enum class StandardInputType {
    Source, Caret, IO
}

enum class OperatorType {
    MOVE_RIGHT {
        override val input = StandardInputType.Source
        override val instructionConstructor = ::MoveInstruction
    },
    MOVE_LEFT {
        override val input = StandardInputType.Source
        override val instructionConstructor = ::MoveInstruction
    },
    MOVE_UP {
        override val input = StandardInputType.Source
        override val instructionConstructor = ::MoveInstruction
    },
    MOVE_DOWN {
        override val input = StandardInputType.Source
        override val instructionConstructor = ::MoveInstruction
    },
    ADD {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    MULTIPLY {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    SUBTRACT {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    DIVIDE {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    MODULO {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    AND {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    OR {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    XOR {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    FLIP {
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    INCREASE{
        override val input = StandardInputType.Source
        override val instructionConstructor = ::VaryInstruction},
    DECREASE{
        override val input = StandardInputType.Source
        override val instructionConstructor = ::VaryInstruction},
    COPY{ override val instructionConstructor = ::CopyInstruction},
    STORE {
        override val input = StandardInputType.Source
        override val instructionConstructor = ::StoreInstruction
    },
    READ_INPUT {
        override val input = StandardInputType.IO
        override val instructionConstructor = ::ReadInstruction
    },
    PRINT_OUTPUT {
        override val input = StandardInputType.IO
        override val instructionConstructor = ::PrintInstruction
    },
    BEGIN_LOOP {
        override val isBracket = true
        override val instructionConstructor = ::BracketInstruction
    },
    END_LOOP {
        override val isBracket = true
        override val instructionConstructor = ::BracketInstruction
    },
    BEGIN_Q_LOOP {
        override val isBracket = true
        override val instructionConstructor = ::BracketInstruction
    },
    END_Q_LOOP {
        override val isBracket = true
        override val instructionConstructor = ::BracketInstruction
    },
    BEGIN_IF {
        override val isBracket = true
        override val instructionConstructor = ::BracketInstruction // maybe its own instruction
    },
    END_IF {
        override val isBracket = true
        override val instructionConstructor = ::BracketInstruction
    },
    CONFIGURE_READHEAD{
        override val input = StandardInputType.Source
    },
    EXECUTE,
    EXIT_EXECUTION,
    NEW_CARET,
    CHANGE_CARET,
    QUERY_ENVIRONMENT{
        override val input = StandardInputType.Source
    },
    START_FUNCTION,
    CHARACTER,
    MODIFIER;

    open val input: StandardInputType = StandardInputType.Caret
    open val usesStack: Boolean = false
    open val isBracket: Boolean = false
    abstract val instructionConstructor: (op: Char, modify: Boolean, input: ArrayList<Char>, type: OperatorType) -> Instruction

    fun createInstruction(op: Char, modify: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction {
        return instructionConstructor(op, modify, input, type)
    }

}