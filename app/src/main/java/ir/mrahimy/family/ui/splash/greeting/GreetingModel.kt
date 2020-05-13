package ir.mrahimy.family.ui.splash.greeting

import ir.mrahimy.family.base.BaseModel
import ir.mrahimy.family.data.pojo.Greeting

class GreetingModel : BaseModel() {

    private val greetingList = listOf(
        Greeting("Bem-vindo"),
        Greeting("Benvenuti"),
        Greeting("Willkommen"),
        Greeting("Salvete"),
        Greeting("Welcome"),
        Greeting("Bienvenidos"),
        Greeting("Welkom"),
        Greeting("Mabuhay")
    )

    fun randomGreeting(): Greeting {
        return greetingList.random()
    }
}