package sixthDay

import Solution
import secondDay.IdsData
import util.FileUtil
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.min
import kotlin.text.digitToInt

// 6371789547734
// Second
// 11418832945720 too low
// 11419862653216
enum class Operation(val code: Char, val neutral: Long) {
    ADD(code = '+', neutral = 0), SUBTRACT(code = '-', neutral = 0), DIVIDE(code = '/', neutral = 1), MULTIPLY(code = '*', neutral = 1), INVALID(code = 'I', neutral = 0);

    companion object {
        fun fromCode(code: Char): Operation {
            return Operation.entries.find { it.code == code } ?: INVALID
        }
    }
}

data class OperationsData(
    val values: List<Long>,
    val operation: Operation
) {
    fun computeResult(): Long {
        return when (operation) {
            Operation.ADD -> values.sum()
            Operation.SUBTRACT -> values.reduce { acc, l -> acc - l }
            Operation.MULTIPLY -> values.reduce { acc, l -> acc * l }
            Operation.DIVIDE -> values.reduce { acc, l -> acc / l }
            Operation.INVALID -> 0L
        }
    }
}

class SixthDay: Solution {
    override fun solveFirst(): Any {
        val data = readData()
        val inverted = transposeMatrix(matrix = data)
        val operationsData = parseMatrix(matrix = inverted)

        var total = 0L
        operationsData.forEach {
            total += it.computeResult()
        }
        return total
    }

    override fun solveSecond(): Any {
        val operationsData = readColumns()

        var total = 0L
        operationsData.forEach {
            total += it.computeResult()
        }
        return total
    }

    private fun transposeMatrix(matrix: List<List<String>>): List<List<String>> {
        val maxCols = matrix.maxOfOrNull { it.size } ?: 0
        return (0 until maxCols).map { col ->
            matrix.map { row -> row.getOrNull(col) ?: "" }
        }
    }

    private fun parseMatrix(matrix: List<List<String>>): MutableList<OperationsData> {
        val operationsData: MutableList<OperationsData> = mutableListOf()
        matrix.forEach {
            var operation: Operation = Operation.INVALID
            val values: MutableList<Long> = mutableListOf()
            for(i in it.size - 1 downTo 0) {
                if (i == it.size - 1) {
                    operation = Operation.fromCode(code = it[i].first())
                } else {
                    values.add(it[i].toLong())
                }
            }
            operationsData.add(OperationsData(values = values, operation = operation))
        }
        return operationsData
    }

    private fun readData(): List<List<String>> {
        val lines = FileUtil.readFileAsList("/sixth_day.txt")

        return lines.mapNotNull { raw ->
            val line = raw.trim()
            if (line.isEmpty()) return@mapNotNull null
            splitLineToParts(line)
        }
    }

    private fun readDataSecond(): List<List<String>> {
        val all = FileUtil.readFileAsList("/sixth_day.txt")

        val last = all.last()

        // find column start indices from the last line: any non-space char marks a column start
        val starts = last.indices.filter { i -> last[i] != ' ' }

        // build ranges [start .. nextStart) for each column
        val ranges = starts.mapIndexed { idx, start ->
            val end = if (idx + 1 < starts.size) starts[idx + 1] else last.length
            start until end
        }

        // extract each column from all data lines, trim each cell
        return transposeMatrix(ranges.map { range ->
            all.map { line ->
                if (line.length <= range.first) ""
                else {
                    val endIndex = min(line.length, range.last + 1)
                    line.substring(range.first, endIndex)
                }
            }
        })
    }

    private fun readColumns(): MutableList<OperationsData> {
        val rows = readDataSecond()
        return transposeMatrixSecond(rows)
    }

    private fun transposeMatrixSecond(matrix: List<List<String>>): MutableList<OperationsData> {
        val operationsData: MutableList<OperationsData> = mutableListOf()
        val maxCols = matrix.maxOfOrNull { it.size } ?: 0
        val new = (0 until maxCols).map { col ->
            matrix.map { row -> row.getOrNull(col) ?: "" }
        }.toMutableList()

        for (i in new.indices) {
            val operation = Operation.fromCode(code = new[i][new[i].size - 1].first())
            val colValues = new[i].toMutableList()
            if (colValues.isNotEmpty()) colValues.removeAt(colValues.lastIndex)

            val maxLen = colValues.maxOfOrNull { it.length } ?: 0
            val generated = (maxLen - 1 downTo  0).map { pos ->
                val sb = StringBuilder()
                for (item in colValues) {
                    item.getOrNull(pos)?.let { sb.append(it) }
                }
                val s = sb.toString()
                if (s.isBlank()) operation.neutral else s.trim().toLongOrNull()
            }
            operationsData.add(OperationsData(values = generated as List<Long>, operation = operation))
        }
        return operationsData
    }

    fun splitLineToParts(line: String): List<String> =
        line.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
}