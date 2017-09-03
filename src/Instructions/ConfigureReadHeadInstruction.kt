package Instructions
import OperatorType
import ExecutionTrack
import Extensions.toInt

class ConfigureReadHeadInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType) : Instruction(operator, modifyInputChannel, input, type) {

    override fun execute(environment: ExecutionTrack) {
        if(modifyInputChannel){
            environment.configureReadHead()
        } else if (input.isEmpty()){
            environment.configureReadHead(true)
        } else {
            environment.configureReadHead(value = input.toInt())
        }
    }


}