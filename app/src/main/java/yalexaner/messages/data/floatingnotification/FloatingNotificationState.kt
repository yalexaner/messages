package yalexaner.messages.data.floatingnotification

import androidx.compose.animation.core.MutableTransitionState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FloatingNotificationState @Inject constructor() {

    val visibilityState = MutableTransitionState(false)

    private var _text = MutableLiveData<String>(null)
    val text: LiveData<String> = _text

    private var job: Job? = null

    fun showNotification(text: String, timeShowing: Long = SHORT_SHOWING_TIME) {
        hideNotification()

        job = CoroutineScope(Dispatchers.Main).launch {
            while (!visibilityState.isIdle) delay(5L)

            visibilityState.targetState = true
            _text.value = text

            delay(timeShowing)

            visibilityState.targetState = false
        }
    }

    private fun hideNotification() {
        visibilityState.targetState = false
        job?.cancel()
    }

    companion object {

        const val SHORT_SHOWING_TIME = 2_000L
    }
}