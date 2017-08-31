import java.io.File
import java.nio.file.Paths
import java.nio.file.Files
import Extensions.*

fun main(args : Array<String>) {

    val pathName = "D:\\Files\\Code\\IntelliJ\\Bitopiary\\testFiles\\testLexing.bty"

//    var b = Mod3Stack(ExecutionTrack()) { x, y -> x }
//
//    for ( y in 1..12) {
//        b.executeInstruction()
//    }

    println("${Integer.parseInt("000000000000000")} + hi" )

    val inputToCommand = ArrayList<Char>()
    inputToCommand.add('9')
    inputToCommand.add('1')
    inputToCommand.add('1')
    inputToCommand.add('0')
    inputToCommand.add('0')
    inputToCommand.add('4')

    print("${'2'.toInt()}HIIIII   ${inputToCommand.toInt()} and the beat goes on\n")
    print("${'2'.toInt()}HIIIII   ${inputToCommand.toInt()} and the beat goes on\n")


    var interpreter = BitopiaryLexer(filePath = pathName)
    interpreter.program.run()
    if (args.size == 0) {
        println("${'.'.isDigit()}Please provide a name as a command-line argument")
        return
    }

}