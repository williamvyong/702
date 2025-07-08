// 文件路径: app/src/main/java/com/williamv/debtmake/viewmodel/ForgotPasswordViewModel.kt
package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    // 创建 Supabase 客户端（通过环境变量）
    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = System.getenv("SUPABASE_URL") ?: "",
        supabaseKey = System.getenv("SUPABASE_ANON_KEY") ?: ""
    ) {
        install(io.github.jan.supabase.gotrue.GoTrue)
    }

    // 用户输入邮箱
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun onEmailChanged(value: String) {
        _email.value = value
    }

    // 状态通知
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // 执行密码重置邮件发送
    fun sendResetEmail() {
        val inputEmail = email.value.trim()

        if (inputEmail.isBlank()) {
            _errorMessage.value = "Email cannot be empty"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null
        _successMessage.value = null

        viewModelScope.launch {
            try {
                supabase.gotrue.resetPasswordForEmail(inputEmail)
                _successMessage.value = "Password reset email sent. Please check your inbox."
            } catch (e: Exception) {
                _errorMessage.value = "Failed to send reset email: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
