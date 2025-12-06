package thirdDay

import Solution
import util.FileUtil
import kotlin.text.digitToInt

//17281
//171388730430281
data class BatteryData(
    val batteryValues: String
)

class ThirdDay: Solution {
    override fun solveFirst(): Any {
        val data = parseIdsData()

        var sum = 0L
        var count = 0
        data.forEach {
            println("Count: ${count++}")
            val firstIndex = check(data = it)
            val firstMax = it.batteryValues[firstIndex].digitToInt()

            val secondIndex = check(data = it, index = firstIndex + 1, lastIndex = 0)
            val secondMax = it.batteryValues[secondIndex].digitToInt()

            val numericValue = "$firstMax$secondMax".toLong()
            sum += numericValue
        }
        return sum
    }

    private fun check(data: BatteryData, index: Int = 0, lastIndex: Int = 1): Int {
        var maxValue = data.batteryValues[index].digitToInt()
        var maxIndex = index
        for(i in index until data.batteryValues.length - lastIndex) {
            if (maxValue == 9) {
                break
            }
            val value = data.batteryValues[i].digitToInt()
            if (value > maxValue) {
                maxValue = value
                maxIndex = i
            }
        }
        return maxIndex
    }

    override fun solveSecond(): Any {
        val data = parseIdsData()

        var sum = 0L
        val indexArray = IntArray(12)

        data.forEach { it ->
            var newIndex = 0
            for (i in 0 until 12) {
                newIndex = check(data = it, index = newIndex, lastIndex = 11 - i)
                indexArray[i] = it.batteryValues[newIndex].digitToInt()
                newIndex++
            }
            val numericValue: Long = indexArray.joinToString(separator = "") { item -> item.toString() }.toLong()
            sum += numericValue
        }
        return sum
    }

    private fun parseIdsData(): List<BatteryData> =
        FileUtil.readFileAsList("/third_day.txt").map {
            BatteryData(batteryValues = it)
        }
}