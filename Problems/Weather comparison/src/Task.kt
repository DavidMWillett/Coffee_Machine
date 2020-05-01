data class City(val name: String) {
    var degrees: Int = 0
        set(value) {
            field = if (value < -92 || value > 57) {
                when (name) {
                    "Dubai" -> 30
                    "Moscow" -> 5
                    "Hanoi" -> 20
                    else -> value
                }
            } else {
                value
            }
        }
}        

fun main() {
    val first = readLine()!!.toInt()
    val second = readLine()!!.toInt()
    val third = readLine()!!.toInt()
    val firstCity = City("Dubai")
    firstCity.degrees = first
    val secondCity = City("Moscow")
    secondCity.degrees = second
    val thirdCity = City("Hanoi")
    thirdCity.degrees = third

    print(when {
        firstCity.degrees < secondCity.degrees && firstCity.degrees < thirdCity.degrees
            -> firstCity.name
        secondCity.degrees < firstCity.degrees && secondCity.degrees < thirdCity.degrees
            -> secondCity.name
        thirdCity.degrees < firstCity.degrees && thirdCity.degrees < secondCity.degrees
            -> thirdCity.name
        else -> "neither"
    })
}
