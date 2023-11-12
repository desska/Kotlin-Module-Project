class App(private val input: Input) {

    private var sc = ScreenType.ARCH_SELECT
    var isRunning = true

    private var archList: MutableList<Arch> = mutableListOf()
    private var menu: MutableMap<Int, MenuItem> = mutableMapOf()
    private var currentArch = Arch("")
    private var currentNote = Note("", "")

    private fun changeScreen(sc: ScreenType) {

        if (sc == ScreenType.EXIT) {
            isRunning = false
        }

        this.sc = sc
        update()
        showMenu()

    }

    fun update() {

        menu.clear()
        when (sc) {

            ScreenType.ARCH_SELECT, ScreenType.NOTE_SELECT -> {

                menu[0] = MenuItem("Создать новый") { changeScreen(createSc(sc)) }

                if (sc == ScreenType.ARCH_SELECT) {
                    val list = archList
                    list.forEachIndexed { index, item -> menu[index + 1] = MenuItem(item.name, null) }
                } else {
                    val list = currentArch.notes
                    list.forEachIndexed { index, item -> menu[index + 1] = MenuItem(item.name, null) }
                }
                menu[menu.size] = (MenuItem("Выход\n") {
                    changeScreen(if (sc == ScreenType.ARCH_SELECT) ScreenType.EXIT else ScreenType.ARCH_SELECT)
                })

            }

            ScreenType.ARCH_CREATE, ScreenType.NOTE_CREATE -> {

                val isNeedDesc = sc == ScreenType.NOTE_CREATE

                if (sc == ScreenType.ARCH_CREATE) currentArch = Arch("")
                else currentNote = Note("", "")

                menu[menu.size] = MenuItem("Ввести имя") {
                    println("Введите имя:")
                    val name = input.nextLine()
                    if (sc == ScreenType.ARCH_CREATE) currentArch.name = name else currentNote.name = name
                }

                if (isNeedDesc) {
                    menu[menu.size] = MenuItem("Ввести текст") {
                        println("Введите текст заметки:")
                        val desc = input.nextLine()
                        currentNote.desc = desc
                    }
                }

                menu[menu.size] = (MenuItem("Сохранить и выйти") {

                    if (sc == ScreenType.ARCH_CREATE) {
                        if (currentArch.isReadyToSave()) {
                            archList.add(currentArch)
                            changeScreen(selectSc(sc))
                        }
                    } else if (sc == ScreenType.NOTE_CREATE)
                        if (currentNote.isReadyToSave()) {
                            currentArch.notes.add(currentNote)
                            changeScreen(selectSc(sc))
                        }

                })

                menu[menu.size] = MenuItem("Выход\n") {

                    changeScreen(selectSc(sc))

                }

            }

            ScreenType.NOTE_TEXT -> {

                menu[menu.size] = MenuItem("Выход\n") {

                    changeScreen(selectSc(sc))

                }

            }

            else -> {

            }
        }

    }

    fun showMenu() {

        println(sc.header)

        if (sc == ScreenType.NOTE_TEXT) println(currentNote.toString())

        val sortedMenu = menu.toSortedMap()
        for (i in sortedMenu) {
            println("${i.key}: ${i.value}")
        }
    }

    fun isMenuKeyCorrect(key: Int) = menu.containsKey(key)

    fun onClick(key: Int) {

        val item = menu[key] ?: return
        if (item.func != null) {

            item.func?.invoke()
            return

        }

        when (sc) {

            ScreenType.ARCH_SELECT, ScreenType.NOTE_SELECT -> {

                if (sc == ScreenType.ARCH_SELECT) {
                    currentArch = archList[key - 1]
                } else {
                    currentNote = currentArch.notes[key - 1]
                }

                changeScreen(selectSc(sc))

            }

            else -> {

            }

        }

    }

    private fun createSc(sc: ScreenType): ScreenType {

        return when (sc) {
            ScreenType.ARCH_SELECT -> ScreenType.ARCH_CREATE
            ScreenType.NOTE_SELECT -> ScreenType.NOTE_CREATE
            else -> {
                ScreenType.EXIT
            }
        }
    }

    private fun selectSc(sc: ScreenType): ScreenType {
        return when (sc) {
            ScreenType.ARCH_CREATE -> ScreenType.ARCH_SELECT
            ScreenType.NOTE_CREATE -> ScreenType.NOTE_SELECT
            ScreenType.ARCH_SELECT -> ScreenType.NOTE_SELECT
            ScreenType.NOTE_SELECT -> ScreenType.NOTE_TEXT
            ScreenType.NOTE_TEXT -> ScreenType.NOTE_SELECT
            else -> {
                ScreenType.EXIT
            }
        }
    }


}