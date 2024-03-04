package com.innovativetools.ai.myaiassistant.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    var _keyboardFocus = MutableStateFlow(false)
    val keyboardFocus: StateFlow<Boolean>  = _keyboardFocus

    fun updateKeyboardFocus(hasFocus: Boolean) {
        _keyboardFocus.value = hasFocus
    }
}
