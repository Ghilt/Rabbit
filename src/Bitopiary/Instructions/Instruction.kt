package Bitopiary.Instructions

import Bitopiary.BitopiarySyntax
import Bitopiary.ExecutionState.ExecutionTrack
import Extensions.toLogString
import Bitopiary.OperatorType
import Bitopiary.toOperator

abstract class Instruction (val operator: Char, internal val modifyInputChannel: Boolean, internal val input: ArrayList<Char>, internal val type: OperatorType){
    abstract fun execute(environment: ExecutionTrack)
    fun restrictedExecute(environment: ExecutionTrack) {
        if (!type.isRestrictable){
            execute(environment)
        }
    }

    fun toLogString() = "${operator.toOperator()} $operator${if (modifyInputChannel) "${BitopiarySyntax.commandModifier.toCharacter[0]}${input.toLogString()}" else input.toLogString()}"

}