import Instructions.*
import Extensions.*

object BitopiarySyntax {

    val parallelExecution = '#'
    val commandModifier = '.'

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
    '¤', 0.toChar() -> OperatorType.EXIT_EXECUTION
    '?' -> OperatorType.QUERY_ENVIRONMENT
    ':' -> OperatorType.PRINT_OUTPUT
    '=' -> OperatorType.READ_INPUT
    '#' -> OperatorType.START_FUNCTION
    '.' -> OperatorType.MODIFIER
    '0','1','2','3','4','5','6','7','8','9',
    'A','B','C','D','E','F','G','H','I','J',
    'K','L','M','N','O','P','Q','R','S','T',
    'U','V','W','X','Y','Z','a','b','c','d',
    'e','f','g','h','i','j','k','l','m','n',
    'o','p','q','r','s','t','u','v','w','x',
    'y','z' -> OperatorType.CHARACTER
    else -> throw Error("Syntax Error: character is not allowed ${this}, ${this.toInt()}")
}

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
        override fun matches(type: OperatorType) = type == END_LOOP
        override val instructionConstructor = ::BracketInstruction
    },
    END_LOOP {
        override val isBracket = true
        override fun matches(type: OperatorType) = type == BEGIN_LOOP
        override val instructionConstructor = ::BracketInstruction
    },
    BEGIN_Q_LOOP {
        override val isBracket = true
        override fun matches(type: OperatorType) = type == END_Q_LOOP
        override val instructionConstructor = ::BracketInstruction
    },
    END_Q_LOOP {
        override val isBracket = true
        override fun matches(type: OperatorType) = type == BEGIN_Q_LOOP
        override val instructionConstructor = ::BracketInstruction
    },
    BEGIN_IF {
        override val isBracket = true
        override val isRestrictable = false
        override fun matches(type: OperatorType) = type == END_IF
        override val instructionConstructor = ::ConditionalInstruction
    },
    END_IF {
        override val isBracket = true
        override val isRestrictable = false
        override fun matches(type: OperatorType) = type == BEGIN_IF
        override val instructionConstructor = ::ConditionalInstruction
    },
    CONFIGURE_READHEAD{
        override val instructionConstructor = ::BracketInstruction // TODO
        override val input = StandardInputType.Source
    },
    EXECUTE{
        override val instructionConstructor = ::BracketInstruction // TODO
    },
    EXIT_EXECUTION{
        override val instructionConstructor = ::TerminateExecutionInstruction
    },
    NEW_CARET{
        override val instructionConstructor = ::BracketInstruction // TODO
    },
    CHANGE_CARET{
        override val instructionConstructor = ::BracketInstruction // TODO
    },
    QUERY_ENVIRONMENT{
        override val input = StandardInputType.Source
        override val instructionConstructor = ::BracketInstruction // TODO

    },
    START_FUNCTION{
        override val instructionConstructor = ::BracketInstruction // TODO
    },
    CHARACTER{
        override val instructionConstructor = ::CharacterInstruction
    },
    MODIFIER{
        override val instructionConstructor = ::BracketInstruction // TODO
    };

    open val input: StandardInputType = StandardInputType.Caret
    open val usesStack: Boolean = false
    open val isBracket: Boolean = false
    open val isRestrictable: Boolean = true

    abstract val instructionConstructor: (op: Char, modify: Boolean, input: ArrayList<Char>, type: OperatorType) -> Instruction

    fun createInstruction(op: Char, modify: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction {
        return instructionConstructor(op, modify, input, type)
    }
    open fun matches(type: OperatorType): Boolean = false

}