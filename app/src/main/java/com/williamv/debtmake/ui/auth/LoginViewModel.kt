package com.williamv.debtmake.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.williamv.debtmake.data.AuthService

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    /** 用户在 UI 输入时调用 */
    fun onEmailChange(new: String) {
        email = new
    }
    fun onPasswordChange(new: String) {
        password = new
    }

    /** 点击“登录”按钮时调用 */
    suspend fun login(): Boolean {
        return try {
            authService.signIn(email.trim(), password)
            true
        } catch (e: Exception) {
            errorMessage = e.message
            false
        }
    }
}
