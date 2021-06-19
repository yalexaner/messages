package yalexaner.messages.data.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import yalexaner.messages.data.floatingnotification.FloatingNotificationState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val floatingNotificationState: FloatingNotificationState
) : ViewModel()