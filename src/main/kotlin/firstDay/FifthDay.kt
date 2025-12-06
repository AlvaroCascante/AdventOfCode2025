package firstDay

import Solution
import util.FileUtil

// 517

// Second
// 305695556161229 too low
// 417882371140811 too high
// 311515021215341 too low
// 270633644033548
// 290000848033643
// 326397780089535
// 326397780089535
// 326244978604823
// 291906741668813
// 326244978604823
// 336173027056994
data class FreshRanges(
    val init: Long,
    val end: Long
)

data class IngredientsData(
    val ranges: List<FreshRanges>,
    val ingredients: List<Long>
)

class FifthDay: Solution {

    override fun solveFirst(): Any {
        val data = FileUtil.readFileAsList("/fifth_day.txt")
        val ingredientsData = parseData(data)

        val ranges = ingredientsData.ranges
        val ingredients = ingredientsData.ingredients
        var count = 0
        ingredients.forEach {
            for (range in ranges) {
                if (it < range.init) {
                    break
                }
                if (it <= range.end) {
                    count ++
                    break
                }
            }
        }
        return count
    }

    override fun solveSecond(): Any {
        val data = FileUtil.readFileAsList("/fifth_day.txt")
        val ranges = parseSecondData(data).toMutableList()

        var total = 0L
        var maxEnd = -1L
        for (r in ranges) {
            val newStart = maxOf(r.init, maxEnd + 1)
            if (newStart <= r.end) {
                total += (r.end - newStart + 1)
                maxEnd = r.end
            }
        }
        return total
    }

    private fun parseData(lines: List<String>): IngredientsData {
        val ranges = mutableListOf<FreshRanges>()
        val ingredients = mutableListOf<Long>()

        var parsingRanges = true
        for (raw in lines) {
            val line = raw.trim()
            if (line.isEmpty()) {
                parsingRanges = false
                continue
            }
            if (parsingRanges) {
                val parts = line.split("-")
                require(parts.size == 2) { "Invalid range line: $line" }
                val start = parts[0].toLong()
                val end = parts[1].toLong()
                if (start < end) {
                    ranges.add(FreshRanges(init = start, end = end))
                } else {
                    ranges.add(FreshRanges(init = end, end = start))
                }
            } else {
                ingredients += line.toLong()
            }
        }

        return IngredientsData(ranges.sortedBy { it.init }, ingredients.sorted())
    }

    private fun parseSecondData(lines: List<String>): List<FreshRanges> {
        val ranges = mutableListOf<FreshRanges>()

        var parsingRanges = true
        for (raw in lines) {
            val line = raw.trim()
            if (line.isEmpty()) {
                parsingRanges = false
                continue
            }
            if (parsingRanges) {
                val parts = line.split("-")
                require(parts.size == 2) { "Invalid range line: $line" }
                val start = parts[0].toLong()
                val end = parts[1].toLong()
                if (start < end) {
                    ranges.add(FreshRanges(init = start, end = end))
                } else {
                    ranges.add(FreshRanges(init = end, end = start))
                }
            }
        }

        return ranges.sortedWith(comparator = compareBy(FreshRanges::init, FreshRanges::end))
    }
}