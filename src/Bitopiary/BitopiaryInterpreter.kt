package Bitopiary

fun main(args : Array<String>) {

    if (args.isEmpty()) {
        println("Please provide a filepath as a command-line argument")
    }

    val pathName = "D:\\Files\\Code\\IntelliJ\\Bitopiary\\testFiles\\testLexing.bty"

    val interpreter = BitopiaryLexer(filePath = pathName)
    interpreter.program.run()


}