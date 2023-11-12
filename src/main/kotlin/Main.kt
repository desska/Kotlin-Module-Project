import java.util.Scanner

fun main(args: Array<String>) {

    val scanner = Scanner(System.`in`)
    val input = Input(scanner)

    val app = App(input)
    app.update()
    app.showMenu()

    while (true) {

        if (!app.isRunning) {
            break
        }

        val key = input.nextInt()
        if (!app.isMenuKeyCorrect(key)) {
            println("некорректный пункт меню\n")
            app.showMenu()
            continue
        }

        app.onClick(key)

    }

}





