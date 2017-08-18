import java.io.File
import java.nio.file.Paths
import java.nio.file.Files

fun main(args : Array<String>) {

    val pathName = "D:\\Files\\Code\\IntelliJ\\Bitopiary\\testFiles\\testLexing.bty"

//    val file = File(pathName)
//
//    for(line in file.readLines()){
//        println(line)
//
//    }
//
//    println("_______")
//
//    val reader = file.reader()
//        while (reader.ready()){
//        println(message = "${reader.read().toChar()}")
//    }

//    reader.close()
//    val stream = Files.newInputStream(Paths.get(pathName))
//    stream.buffered().reader().use { reader ->
//        println(reader.readText())
//    }


    var interpreter = BitopiaryLexer(filePath = pathName)

    if (args.size == 0) {
        println("Please provide a name as a command-line argument")
        return
    }

}