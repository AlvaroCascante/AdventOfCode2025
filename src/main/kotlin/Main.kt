import firstDay.FirstDay

fun main() {
    val exercise = FirstDay()
    val response = exercise.solveFirst()
    println("Response: $response")
}

interface Solution {

    fun solveFirst(): Any

    fun solveSecond(): Any
}