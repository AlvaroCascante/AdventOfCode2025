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
        val data = parseIdsData()

        var sum = 0L
        data.forEach {
            var id = it.initId
            while (id <= it.endId) {
                var size = 1
                val idString = id.toString()
                while (size <= idString.length / 2) {
                    if (check(idString, size)) {
                        sum += id
                        break
                    } else {
                        size++
                    }
                }
                id ++
            }
        }
        return sum
    }

    private fun check(text: String, size: Int): Boolean {
        if (text.length % size != 0) return false
        val subText = text.take(size)
        for (i in size until text.length step size) {
            val next = text.substring(i, i + size)
            if (subText != next) {
                return false
            }
        }
        return true
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