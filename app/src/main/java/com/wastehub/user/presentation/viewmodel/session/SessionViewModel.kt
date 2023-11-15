package com.wastehub.user.presentation.viewmodel.session

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wastehub.user.common.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val prefs: SessionManager
): ViewModel() {

    val token = MutableLiveData<String?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.getAccessToken().collect {
                withContext(Dispatchers.Main) {
                    token.value = it
                }
            }
        }
    }

    fun saveToken(accessToken: String, refreshToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.saveToken(accessToken, refreshToken)
        }
    }

    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.deleteToken()
        }
    }


    fun saveSession(session: Boolean) {
        viewModelScope.launch {
            prefs.saveSession(session)
        }
    }


}