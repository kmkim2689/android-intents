package com.practice.intent_practice

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var uri: Uri? by mutableStateOf(null)
        private set

    fun updateUri(uri: Uri?) {
        this.uri = uri
    }
}