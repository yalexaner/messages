package yalexaner.messages.data.floatingnotification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FloatingNotificationState @Inject constructor() {

    private val floatingNotificationScope = Dispatchers.Main

    private val _showing = MutableLiveData(false)
    val showing: LiveData<Boolean> = _showing

    var text: String? = null
        private set

    fun showNotification(text: String, timeShowing: Long = SHORT_SHOWING_TIME) {
        GlobalScope.launch(floatingNotificationScope) {
            _showing.value = true
            this@FloatingNotificationState.text = text

            delay(timeShowing)

            _showing.value = false
        }
    }

    companion object {

        const val SHORT_SHOWING_TIME = 2_000L
    }
}