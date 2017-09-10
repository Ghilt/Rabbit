package Bitopiary.ExecutionState

class ReadHead(var width: Int = 4, var height :Int = 2) {
    val size: Int
        get() = width * height

    var nextWidth = 0

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