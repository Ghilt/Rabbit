package Bitopiary

import Bitopiary.ExecutionState.ReadHead
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.reflect.KFunction1

class InterpreterFlagManager(args: Array<String>) {

    private val FLAG_READHEAD = "-r"
    private val FLAG_MIXED_INPUT = "-m"
    private val FLAG_CHARACTERS_INPUT = "-c"
    private val FLAG_FILE_INPUT = "-f"

    data class Input(val content: String, val isDigit: Boolean)

    val inputs =  ArrayList<Input>()

    var readHeadConfig = ReadHead()

    init {

        var consumer = this::doNotConsume
        for (index in 1 until args.size){

            val success = consumer(args[index])
            consumer = this::doNotConsume
            if (success) continue

            consumer = when (args[index]) {
                FLAG_READHEAD -> this::consumeReadHead
                FLAG_MIXED_INPUT -> this::consumeMixedInput
                FLAG_CHARACTERS_INPUT -> this::consumeCharacterInput
                FLAG_FILE_INPUT -> this::consumeFileInput
                else -> consumeDirectly(args[index])
            }
        }
    }

    private fun consumeReadHead(arg: String) :Boolean{
        val config = arg.split(',')

        if(config.size == 2 ) {
            readHeadConfig = ReadHead(config[0].toInt(), config[1].toInt())
            return true
        } else {
            throw IllegalArgumentException("Read head configuration not valid: $arg")
        }
    }

    private fun consumeMixedInput(arg: String) :Boolean{
        inputs.add(Input(arg, arg.toIntOrNull() != null));
        return true
    }

    private fun consumeCharacterInput(arg: String) :Boolean{
        inputs.add(Input(arg, false))
        return true
    }

    private fun consumeFileInput(arg: String) :Boolean{
        val stream = Files.newInputStream(Paths.get(arg))
        var fileString = ""
        stream.buffered().reader().use { reader ->
            fileString = reader.readText()
        }
        inputs.add(Input(fileString, false))
        return true
    }

    private fun doNotConsume(arg: String) :Boolean{
        return false
    }

    private fun consumeDirectly(arg: String) : KFunction1<@ParameterName(name = "arg") String, Boolean> {
        consumeMixedInput(arg)
        return this::doNotConsume
    }
}