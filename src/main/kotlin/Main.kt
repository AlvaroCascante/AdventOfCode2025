import firstDay.FifthDay
import fourthDay.FourthDay
import secondDay.SecondDay
import thirdDay.ThirdDay

fun main() {
    val exercise = FifthDay()
    val response = exercise.solveSecond()
    println("Response: $response")
}

interface Solution {

    fun solveFirst(): Any

    fun solveSecond(): Any
}