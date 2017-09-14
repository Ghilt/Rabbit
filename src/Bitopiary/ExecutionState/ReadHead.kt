package Bitopiary.ExecutionState

data class ReadHead(var width: Int = WIDTH, var height :Int = HEIGHT) {

    companion object {
        private var WIDTH = 8
        private var HEIGHT = 4

        fun setDefaults(width: Int, height: Int) {
            WIDTH = width
            HEIGHT = height
        }
    }

    val size: Int
        get() = width * height

    private var nextWidth = 0

    /** Swap dimension affected every time*/
    var configurationAidDimensionFlag = true

    fun configurationChange(value: Int = if (configurationAidDimensionFlag) width*2 else height*2) {
        when (configurationAidDimensionFlag) {
            true -> nextWidth = value
            false -> {
                width = nextWidth
                height = value
            }
        }
        configurationAidDimensionFlag = !configurationAidDimensionFlag

    }

}