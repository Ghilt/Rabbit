package Extensions

import Bitopiary.ExecutionState.LoopStack
import Bitopiary.ExecutionState.ReadHead
import java.util.*

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

fun ArrayList<Char>.toLogString(): String {
    return when {
        this.isEmpty() -> ""
        this.size == 1 -> "${this[0]}"
        else -> "${this.toInt()}"
    }
}

/** Found built in bitwise inversion infix inv()....  x inv y
 * but keeping this as an example */
infix fun Int.flip(y: Int): Int {
    var strThis = Integer.toBinaryString(this)
    var strY = Integer.toBinaryString(y)
    if (this > y){
        strY = strY.padStart(strThis.length,'0')
    } else {
        strThis = strThis.padStart(strY.length,'0')
    }
    val flipped = strThis.zip(strY) {
        a,b -> if (a == b) '0' else '1'
    }

    return Integer.parseInt(flipped.fold(""){ a, c -> a + c}, 2)
}

val boolToBinary = { b: Boolean -> if (b) "1" else "0" }

fun Stack<LoopStack.LoopMarker>.parentIsRestricted(): Boolean = this.isNotEmpty() && this.peek().restrictedLoop