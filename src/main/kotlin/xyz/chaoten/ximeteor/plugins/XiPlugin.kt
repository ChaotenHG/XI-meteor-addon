package xyz.chaoten.ximeteor.plugins

abstract class XiPlugin {

    /** This field is automatically assigned from plugin.json file. */
    val name = "XiPlugin"

    /** This field is automatically assigned from plugin.json file. */
    val authors = arrayOf("Chaoten")

    abstract fun onEnable()

    fun onReload()  {}

    fun onRegisterCategories() {}

}
