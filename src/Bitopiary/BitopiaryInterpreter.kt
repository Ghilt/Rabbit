package Bitopiary

import javax.print.attribute.IntegerSyntax


fun main(args : Array<String>) {

    if (args.isEmpty()) {
        //Testfile: "D:\\Files\\Code\\IntelliJ\\Bitopiary\\testFiles\\testLexing.bty"
        println("Please provide a filepath as a command-line argument")
    }

    val interpreter = BitopiaryLexer(filePath = args[0])
    interpreter.program.run()

}