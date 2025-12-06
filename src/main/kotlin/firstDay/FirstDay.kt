package firstDay

import Solution
import util.FileUtil

//6559 to low
//7513 to high
//7101
enum class Direction(val code: Char) {
    LEFT(code = 'L'), RIGHT(code = 'R'), INVALID(code = 'I');

    companion object {
        fun fromCode(code: Char): Direction {
            return entries.find { it.code == code } ?: INVALID
        }
    }
}

data class RotationData(
    val direction: Direction,
    val degrees: Int,
    val rotations: Int
)

class FirstDay: Solution {

    private val INDEX = 50
    private val MAX_DEGREE = 100
    private val MIN_DEGREE = 0

    override fun solveFirst(): Any {
        val data = FileUtil.readFileAsList("/first_day_ex1.txt")

        val rotations: List<RotationData> = data.mapNotNull { line ->
            val s = line.trim()
            if (s.isEmpty()) return@mapNotNull null
            val degrees = s.substring(1).toIntOrNull() ?: return@mapNotNull null

            normalizeDegreePlusRotations(
                direction = Direction.fromCode(s[0]),
                degrees = degrees
            )
        }

        var index = INDEX
        var count = 0
        var isZero = false

        rotations.forEach {
            print("Rot: ${it.rotations} -- Index: $index")
            when(it.direction) {
                Direction.LEFT -> {
                    print(" - ${it.degrees}")
                    index -= it.degrees
                    count += it.rotations
                }
                Direction.RIGHT -> {
                    print(" + ${it.degrees}")
                    index += it.degrees
                    count += it.rotations
                }

                Direction.INVALID -> println("Invalid rotation")
            }

            print(" = $index")
            if (index == MAX_DEGREE || index == MIN_DEGREE) {
                count ++
                print(" -- 0")
                index = MIN_DEGREE
                isZero = true
            } else if (index < MIN_DEGREE) {
                if (!isZero) {
                    count++
                }
                index += MAX_DEGREE
                print(" -- $index")
                isZero = false
            } else if (index > MAX_DEGREE) {
                count ++
                index -= MAX_DEGREE
                print(" -- $index")
                isZero = false
            } else {
                isZero = false
            }
            println(" -- $index -- Count: $count")
        }
        if (index == 0){
            count ++
        }
        println("Count: $count")
        return count
    }

    private fun normalizeDegree(degrees: Int): Int {
        var deg = degrees
        if (degrees > MAX_DEGREE) {
            deg = degrees % MAX_DEGREE
        }
        return deg
    }

    private fun normalizeDegreePlusRotations(direction: Direction, degrees: Int): RotationData {
        return RotationData(
            direction = direction,
            degrees = normalizeDegree(degrees),
            rotations = degrees / MAX_DEGREE
        )
    }

    override fun solveSecond(): Any {
        TODO("Not yet implemented")
    }
}