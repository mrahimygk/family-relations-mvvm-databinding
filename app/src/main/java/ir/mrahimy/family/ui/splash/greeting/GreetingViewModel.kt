package ir.mrahimy.family.ui.splash.greeting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.mrahimy.family.base.BaseViewModel
import ir.mrahimy.family.data.pojo.Greeting
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GreetingViewModel(model: GreetingModel) : BaseViewModel(model) {

    private val _greeting = MutableLiveData(model.randomGreeting())
    val greeting: LiveData<Greeting>
        get() = _greeting

    init {
        viewModelScope.launch {
            var shouldFinish = false;
            while (!shouldFinish) {
                delay(666)
                _greeting.postValue(model.randomGreeting())
            }
        }
    }
}