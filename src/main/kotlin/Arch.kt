data class Arch(var name: String) {

    val notes: MutableList<Note> = mutableListOf()

    fun isReadyToSave(): Boolean {

        var isError = false
        if (name.isEmpty()) {
            println("Ошибка при сохранении.Пустое имя\n")
            isError = true
        }

        return !isError

    }
}