data class MenuItem(val name:String, val handler: (()-> Unit)?) {

    override fun toString(): String {
        return name
    }

}
