import java.io.File
import java.nio.file.Paths
import java.nio.file.Files

fun main(args : Array<String>) {

    val pathName = "D:\\Files\\Code\\IntelliJ\\Bitopiary\\testFiles\\testLexing.bty"

//    var b = Mod3Stack(ExecutionTrack()) { x, y -> x }
//
//    for ( y in 1..12) {
//        b.executeInstruction()
//    }


    var interpreter = BitopiaryLexer(filePath = pathName)
    interpreter.program.run()
    if (args.size == 0) {
        println("${'.'.isDigit()}Please provide a name as a command-line argument")
        return
    }

}