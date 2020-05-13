package ir.mrahimy.family.ui.splash.greeting

import ir.mrahimy.family.base.BaseModel

class GreetingModel : BaseModel() {

    private val greetingList = listOf(
        "Bem-vindo",
        "Benvenuti",
        "Willkommen",
        "Salvete",
        "Welcome",
        "Bienvenidos",
        "Welkom",
        "Mabuhay"
    )

    fun randomGreeting(): String {
        return greetingList.random()
    }
}