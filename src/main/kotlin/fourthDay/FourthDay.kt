package fourthDay

import Solution
import util.FileUtil
import java.io.File

//1564 too high
//1561 too high
//1560 solution1
data class DataMatrix(
    val index1: Boolean = false,
    val index2: Boolean = false,
    val index3: Boolean = false,
    val index4: Boolean = false,
    val index5: Boolean = false,
    val index6: Boolean = false,
    val index7: Boolean = false,
    val index8: Boolean = false,
    val x: Int = 0,
    val y: Int = 0
) {

    fun isAccessible(): Boolean {
        var count = 0
        if (index1) count++
        if (index2) count++
        if (index3) count++
        if (index4) count++
        if (index5) count++
        if (index6) count++
        if (index7) count++
        if (index8) count++
        return count < 4
    }
}

class FourthDay: Solution {

    val result = mutableListOf<DataMatrix>()

    override fun solveFirst(): Any {
        val data = parseData()
        val count = data.count { it.isAccessible() }
        return count
    }

    override fun solveSecond(): Any {
        val lines = FileUtil.readFileAsList("/fourth_day.txt").toMutableList()

        var total = 0
        do  {
            var count = 0
            checkData(lines = lines)

            result.forEach { data ->
                if (data.isAccessible()) {
                    count++
                    lines[data.x] = replaceCharAt(lines[data.x], data.y, '.')
                }
            }

            println("Total so far: $count")
            total += count
        } while (count > 0)
        return total
    }

    private fun replaceCharAt(s: String, index: Int, newChar: Char): String {
        require(index in 0 until s.length)
        return s.take(index) + newChar + s.substring(index + 1)
    }

    private fun checkData(
        lines: List<String>,
    ){
        result.clear()
        var upLine: String?
        var mainLine: String?

        for (x in lines.indices) {
            upLine = lines.getOrNull(x - 1)
            mainLine = lines[x]
            val bottomLine = lines.getOrNull(x + 1)

            for (i in mainLine.indices) {
                if (mainLine[i] == '@') {
                    result.add(
                        checkLines(
                            x = x,
                            index = i,
                            mainLine = mainLine,
                            upLine = upLine,
                            bottomLine = bottomLine
                        )
                    )
                }
            }
        }
    }

    private fun parseData(): List<DataMatrix> {
        val lines = FileUtil.readFileAsList("/fourth_day.txt")
        val result = mutableListOf<DataMatrix>()

        var mainLine: String? = null
        var upLine: String? = null
        var bottomLine: String? = null
        for(l in lines.indices) {
            upLine = mainLine
            mainLine = bottomLine
            bottomLine = lines[l]
            if(mainLine != null) {
                for (i in mainLine.indices) {
                    if (mainLine[i] == '@') {
                        result.add(checkLines(
                            x = l,
                            index = i,
                            mainLine = mainLine,
                            upLine = upLine,
                            bottomLine = bottomLine)
                        )
                    }
                }
            }
        }

        for (i in 0 until mainLine!!.length) {
            if (bottomLine!![i] == '@') {
                result.add(checkLines(x = mainLine.length -1, index = i, mainLine = bottomLine, upLine = mainLine, bottomLine = null))
            }
        }
        return result
    }
    private fun checkLines(x: Int, index: Int, mainLine: String, upLine: String?, bottomLine: String?): DataMatrix {
        val data = DataMatrix(
            x = x,
            y = index,
            index1 = checkPosition(index = index -1, line = upLine),
            index2 = checkPosition(index = index, line = upLine),
            index3 = checkPosition(index = index +1, line = upLine),
            index4 = checkPosition(index = index -1, line = mainLine),
            index5 = checkPosition(index = index +1, line = mainLine),
            index6 = checkPosition(index = index -1, line = bottomLine),
            index7 = checkPosition(index = index, line = bottomLine),
            index8 = checkPosition(index = index +1, line = bottomLine)
        )

        return data
    }

    private fun checkPosition(index: Int, line: String?): Boolean {
        if (line == null) return false
        if (index < 0 || index >= line.length) {
            return false
        }
        return line[index] == '@'
    }

    fun countAtSymbolsStream(path: String): Long {
        val resourceName = path.removePrefix("/")
        val inputStream = javaClass.getResourceAsStream(path)
            ?: javaClass.classLoader.getResourceAsStream(resourceName)
            ?: File(path).takeIf { it.exists() }?.inputStream()
            ?: File("resources/$resourceName").takeIf { it.exists() }?.inputStream()
            ?: throw java.io.FileNotFoundException("Resource or file not found: $path")

        inputStream.bufferedReader().use { br ->
            var count = 0L
            var ch = br.read()
            while (ch != -1) {
                if (ch.toChar() == '@') count++
                ch = br.read()
            }
            return count
        }
    }
}