package Extensions

fun ArrayList<Char>.toInt() : Int = this.reversed().foldIndexed(0) {
    index, accumulator, unProcessedChar -> accumulator + Math.pow(10.0, index.toDouble()).toInt() * Character.digit(unProcessedChar,10)
}
