class NewExecutionTrackManager(private val parent: BitopiaryProgram){

    private var direction: OperatorType? = null
    private var executionCaret: Caret? = null

    fun feedValue(parameter: Int, caret: Caret) {
        val tDir = direction
        val tExecCaret = executionCaret

        when {
            tDir == null -> this.direction = parameter.toChar().toOperator()
            tExecCaret == null -> executionCaret = Caret(caret)
            else ->
            {
                direction = null
                executionCaret = null
                parent.addExecutionTrack(tExecCaret, Caret(caret), tDir)
            }

        }
    }

}