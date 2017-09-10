package Extensions

fun ArrayList<Char>.toInt() : Int = when (this.size == 1 && !this[0].isDigit()) {
    true -> this[0].toInt()
    false -> {
        this.reversed().foldIndexed(0) {
            index, accumulator, unProcessedChar -> accumulator + Math.pow(10.0, index.toDouble()).toInt() * Character.digit(unProcessedChar, 10)
        }
    }
}

/** Because i could, generic version did not turn out useful */
fun  isomorphCompare(v0: Any, v1: Any, v2: Any, v3: Any, compare: (Any, Any) -> Boolean): Boolean = if (compare(v0, v1)) compare(v2, v3) else !compare(v2, v3)

fun <E> java.util.ArrayList<E>.getElementModulo(index: Int, modulo: Int): E = this[index % modulo]

fun ArrayList<Char>.lastIsDigit() = lastOrNull()?.isDigit() == true

val String.tail: String // Extension property
    get() = drop(1)

public fun ArrayList<Char>.toLogString(): String {
    return when {
        this.isEmpty() -> ""
        this.size == 1 -> "${this[0]}"
        else -> "${this.toInt()}"
    }
}
