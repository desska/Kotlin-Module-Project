data class Note(var name: String, var desc: String) {

    fun isReadyToSave(): Boolean {

        var isError = false
        if (name.isEmpty()) {
            println("Ошибка при сохранении. Пустое имя")
            isError = true
        }

        if (desc.isEmpty()) {
            println("Ошибка при сохранении. Пустой текст заметки")
            isError = true
        }

        return !isError

    }

    override fun toString(): String {
        return "имя:$name \nописание:$desc"
    }
}