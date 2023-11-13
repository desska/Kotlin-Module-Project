import java.lang.NumberFormatException
import java.util.Scanner

class Input(private val scanner: Scanner) {

    fun nextInt(): Int {

        var value: Int = -1
        while (value == -1) {

            try {
                value = scanner.nextLine().toInt()
            } catch (ex: NumberFormatException) {
                println("Введите число")
            }
        }

        return value
    }

    fun nextLine(): String {

        return scanner.nextLine()

    }

}