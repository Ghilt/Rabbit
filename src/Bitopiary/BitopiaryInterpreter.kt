package Bitopiary

import javax.print.attribute.IntegerSyntax


fun main(args : Array<String>) {

    if (args.isEmpty()) {
        println("Please provide a filepath as a command-line argument ${ Integer.toBinaryString((-1) shr 1)}")
    }

    val pathName = "D:\\Files\\Code\\IntelliJ\\Bitopiary\\testFiles\\testLexing.bty"

    val interpreter = BitopiaryLexer(filePath = pathName)
    interpreter.program.run()


}