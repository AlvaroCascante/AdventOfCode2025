package secondDay

import Solution
import util.FileUtil

//21139440284
data class IdsData(
    val initId: Long,
    val endId: Long
)

class SecondDay: Solution {
    override fun solveFirst(): Any {
        val data = parseIdsData()

        var sum = 0L

        data.forEach {
            var id = it.initId
            while (id <= it.endId) {
                val idString = id.toString()
                if (idString.length % 2 == 0) {
                    // even length
                    val first = idString.take(idString.length / 2)
                    val second = idString.substring(idString.length / 2)
                    if (first == second) {
                        sum += id
                    }
                }
                id ++
            }
        }
        return sum
    }

    override fun solveSecond(): Any {
        return "Second Day - Second Exercise"
    }

    private fun parseIdsData(): List<IdsData> {
        val lines = FileUtil.readFileAsList("/second_day.txt")
        val result = mutableListOf<IdsData>()

        for (line in lines) {
            val s = line.trim()
            if (s.isEmpty()) continue

            val groups = s.split(",")
            for (group in groups) {
                val part = group.trim()
                if (part.isEmpty()) continue

                val parts = part.split("-")
                if (parts.size != 2) continue

                val initId = parts[0].trim().toLongOrNull() ?: continue
                val endId = parts[1].trim().toLongOrNull() ?: continue

                result.add(IdsData(initId = initId, endId = endId))
            }
        }

        return result
    }
}