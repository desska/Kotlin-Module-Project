data class MenuItem(val name:String, val func: (()-> Unit)?) {

    override fun toString(): String {
        return name
    }

}
