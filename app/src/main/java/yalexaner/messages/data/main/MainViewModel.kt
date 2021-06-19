package yalexaner.messages.data.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import yalexaner.messages.data.floatingnotification.FloatingNotificationState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val floatingNotificationState: FloatingNotificationState
) : ViewModel() {

    val shouldShowFloatingNotification: LiveData<Boolean> = floatingNotificationState.showing
    val floatingNotificationText: String? get() = floatingNotificationState.text
}