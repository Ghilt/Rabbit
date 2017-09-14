package Bitopiary


fun main(args : Array<String>) {

    if (args.isEmpty()) {
        //Testfile: "D:\\Files\\Code\\IntelliJ\\Bitopiary\\testFiles\\testLexing.bty"
        println("Please provide a filepath as a command-line argument")
    }

    val interpreter = BitopiaryLexer(args[0], InterpreterFlagManager(args))
    interpreter.program.run()

}