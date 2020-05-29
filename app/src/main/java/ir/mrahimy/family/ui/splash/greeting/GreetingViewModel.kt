package ir.mrahimy.family.ui.splash.greeting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseViewModel
import ir.mrahimy.family.data.pojo.Greeting
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GreetingViewModel(private val model: GreetingModel) : BaseViewModel(model) {

    private val _greeting = MutableLiveData(model.randomGreeting())
    val greeting: LiveData<Greeting>
        get() = _greeting

    private val _splashText = MutableLiveData(R.string.splash_text)
    val splashText: LiveData<Int>
        get() = _splashText

    private val _greetingList = MutableLiveData<List<Greeting>>()
    val greetingList: LiveData<List<Greeting>>
        get() = _greetingList

    init {
        viewModelScope.launch {
            var shouldFinish = false;
            while (!shouldFinish) {
                delay(666)
                val currentList = _greetingList.value?.toMutableList() ?: mutableListOf()
                _greetingList.postValue(currentList.apply {
                    add(model.randomGreeting())
                })
            }
        }

        viewModelScope.launch {
            delay(1666)
            checkDestination()
        }
    }

    fun onGreetingClick(greeting: Greeting) {
        showSnackMessage(greeting.title)
    }

    /**
     * not really checking destination as we do not have the logic of login/register->main app
     */
    private fun checkDestination() {
        navigateToWithFinish(
            GreetingFragmentDirections.actionGreetingToFamily(
                _greetingList.value?.last() ?: model.randomGreeting()
            )
        )
    }
}
