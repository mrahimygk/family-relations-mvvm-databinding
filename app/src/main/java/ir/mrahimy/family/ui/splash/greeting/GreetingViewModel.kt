package ir.mrahimy.family.ui.splash.greeting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.mrahimy.family.R
import ir.mrahimy.family.base.BaseViewModel
import ir.mrahimy.family.data.pojo.Greeting
import ir.mrahimy.family.util.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GreetingViewModel(model: GreetingModel) : BaseViewModel(model) {

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
    }

    fun onGreetingClick(greeting: Greeting) {
        _snackMessageString.postValue(Event(greeting.title))
    }
}
