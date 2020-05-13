package ir.mrahimy.family.ui.splash.greeting

import ir.mrahimy.family.base.BaseModel
import ir.mrahimy.family.data.pojo.Greeting
import kotlin.random.Random

class GreetingModel : BaseModel() {

    private val greetingList = listOf(
        Greeting(1, "Bem-vindo"),
        Greeting(2, "Benvenuti"),
        Greeting(3, "Willkommen"),
        Greeting(4, "Salvete"),
        Greeting(5, "Welcome"),
        Greeting(6, "Bienvenidos"),
        Greeting(7, "Welkom"),
        Greeting(8, "Mabuhay")
    )

    fun randomGreeting(): Greeting {
        return greetingList.random().copy(id = Random.nextInt(Int.MAX_VALUE))
    }
}