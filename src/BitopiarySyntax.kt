import Instructions.*

object BitopiarySyntax {

    val parallelExecution = '#' //TODO rethink this
    val commandModifier = OperatorType.MODIFIER

    operator fun contains(ch :Char) : Boolean{
        return OperatorType.values().contains(ch.toOperator()) //Don't optimize when you don't need to right/know what you are doing
    }

}

fun Char.toOperator(): OperatorType {
    return OperatorType.values().firstOrNull { it.toCharacter.contains(this) } ?: throw Exception("Syntax Error: character is not allowed ${this}, ${this.toInt()}")
}

enum class StandardInputType {
    Source, Caret, IO
}

enum class OperatorType {
    MOVE_RIGHT {
        override val toCharacter = charArrayOf('>')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::MoveInstruction
    },
    MOVE_LEFT {
        override val toCharacter = charArrayOf('<')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::MoveInstruction
    },
    MOVE_UP {
        override val toCharacter = charArrayOf('^')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::MoveInstruction
    },
    MOVE_DOWN {
        override val toCharacter = charArrayOf('_')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::MoveInstruction
    },
    ADD {
        override val toCharacter = charArrayOf('+')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    MULTIPLY {
        override val toCharacter = charArrayOf('*')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    SUBTRACT {
        override val toCharacter = charArrayOf('-')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    DIVIDE {
        override val toCharacter = charArrayOf('/')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    MODULO {
        override val toCharacter = charArrayOf('%')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    AND {
        override val toCharacter = charArrayOf('&')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    OR {
        override val toCharacter = charArrayOf('|')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    XOR {
        override val toCharacter = charArrayOf('¨')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    FLIP {
        override val toCharacter = charArrayOf('~')
        override val usesStack = true
        override val instructionConstructor = ::StackInstruction
    },
    INCREASE {
        override val toCharacter = charArrayOf('\'')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::VaryInstruction
    },
    DECREASE {
        override val toCharacter = charArrayOf(',')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::VaryInstruction
    },
    COPY {
        override val toCharacter = charArrayOf('@')
        override val instructionConstructor = ::CopyInstruction
    },
    STORE {
        override val toCharacter = charArrayOf(';')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::StoreInstruction
    },
    READ_INPUT {
        override val toCharacter = charArrayOf('=')
        override val input = StandardInputType.IO
        override val instructionConstructor = ::ReadInstruction
    },
    PRINT_OUTPUT {
        override val toCharacter = charArrayOf(':')
        override val input = StandardInputType.IO
        override val instructionConstructor = ::PrintInstruction
    },
    BEGIN_LOOP {
        override val toCharacter = charArrayOf('[')
        override val isBracket = true
        override fun matches(type: OperatorType) = type == END_LOOP
        override val instructionConstructor = ::BracketInstruction
    },
    END_LOOP {
        override val toCharacter = charArrayOf(']')
        override val isBracket = true
        override fun matches(type: OperatorType) = type == BEGIN_LOOP
        override val instructionConstructor = ::BracketInstruction
    },
    BEGIN_Q_LOOP {
        override val toCharacter = charArrayOf('(')
        override val isBracket = true
        override fun matches(type: OperatorType) = type == END_Q_LOOP
        override val instructionConstructor = ::BracketInstruction
    },
    END_Q_LOOP {
        override val toCharacter = charArrayOf(')')
        override val isBracket = true
        override fun matches(type: OperatorType) = type == BEGIN_Q_LOOP
        override val instructionConstructor = ::BracketInstruction
    },
    BEGIN_IF {
        override val toCharacter = charArrayOf('{')
        override val isBracket = true
        override val isRestrictable = false
        override fun matches(type: OperatorType) = type == END_IF
        override val instructionConstructor = ::ConditionalInstruction
    },
    END_IF {
        override val toCharacter = charArrayOf('}')
        override val isBracket = true
        override val isRestrictable = false
        override fun matches(type: OperatorType) = type == BEGIN_IF
        override val instructionConstructor = ::ConditionalInstruction
    },
    CONFIGURE_READHEAD {
        override val toCharacter = charArrayOf('£')
        override val instructionConstructor = ::ConfigureReadHeadInstruction
        override val input = StandardInputType.Source
    },
    EXECUTE {
        override val toCharacter = charArrayOf('!')
        override val instructionConstructor = ::ExecuteInstruction
    },
    EXIT_EXECUTION {
        override val toCharacter = charArrayOf('¤', 0.toChar())
        override val instructionConstructor = ::TerminateExecutionInstruction
    },
    NEW_CARET {
        override val toCharacter = charArrayOf('$')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::NewCaretInstruction
    },
    CHANGE_CARET {
        override val toCharacter = charArrayOf('\\')
        override val instructionConstructor = ::SwapCaretInstruction
    },
    QUERY_ENVIRONMENT {
        override val toCharacter = charArrayOf('?')
        override val input = StandardInputType.Source
        override val instructionConstructor = ::QueryEnvironmentInstruction

    },
    START_FUNCTION {
        override val toCharacter = charArrayOf('#')
        override val instructionConstructor = ::StartFunctionInstruction
    },
    CHARACTER {
        override val toCharacter = charArrayOf(
                '0','1','2','3','4','5','6','7','8','9',
                'A','B','C','D','E','F','G','H','I','J',
                'K','L','M','N','O','P','Q','R','S','T',
                'U','V','W','X','Y','Z','a','b','c','d',
                'e','f','g','h','i','j','k','l','m','n',
                'o','p','q','r','s','t','u','v','w','x',
                'y','z')
        override val instructionConstructor = ::CharacterInstruction
    },
    NO_OPERATION {
        override val toCharacter = charArrayOf('"')
        override val instructionConstructor = ::NoOpInstruction
    },
    MODIFIER {
        override val toCharacter = charArrayOf('.')
        override val instructionConstructor = ::NoOpInstruction
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
    abstract val toCharacter: CharArray

}