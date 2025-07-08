// ✅ 文件：app/src/main/java/com/williamv/debtmake/viewmodel/LoginViewModel.kt
package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * 登录界面的 ViewModel，负责处理 email/password 输入状态与登录逻辑
 */
class LoginViewModel : ViewModel() {

    // 当前输入的 Email 与 Password
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // 登录是否正在进行中
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // 登录成功标志
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    // 登录失败时的错误消息
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // 设置 Email 和 Password 输入
    fun onEmailChanged(value: String) {
        _email.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    // 执行登录请求
    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            // 此处仅示例登录逻辑，可根据实际需要接入 Supabase
            _loginSuccess.value = email.value.isNotBlank() && password.value.isNotBlank()
            if (!_loginSuccess.value) {
                _errorMessage.value = "Login failed"
            }
            _isLoading.value = false
        }
    }

    // 清除错误信息
    fun clearError() {
        _errorMessage.value = null
    }
}
