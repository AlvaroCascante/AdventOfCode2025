import fifthDay.FifthDay
import sixthDay.SixthDay

fun main() {
    val exercise = SixthDay()
    val response = exercise.solveSecond()
    println("Response: $response")
}

interface Solution {

    fun solveFirst(): Any

    fun solveSecond(): Any
}