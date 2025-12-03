import firstDay.FirstDay
import secondDay.SecondDay

fun main() {
    val exercise = SecondDay()
    val response = exercise.solveFirst()
    println("Response: $response")
}

interface Solution {

    fun solveFirst(): Any

    fun solveSecond(): Any
}