// 文件位置: app/src/main/java/com/williamv/debtmake/viewmodel/SignUpViewModel.kt
package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.UserCredentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    // 通过环境变量初始化 Supabase 客户端
    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = System.getenv("SUPABASE_URL") ?: "",
        supabaseKey = System.getenv("SUPABASE_ANON_KEY") ?: ""
    ) {
        install(io.github.jan.supabase.gotrue.GoTrue)
    }

    // 用户输入状态
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _companyName = MutableStateFlow("")
    val companyName: StateFlow<String> = _companyName

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName

    // 注册中状态
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // 提示信息
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    // 输入更新函数
    fun onEmailChanged(value: String) {
        _email.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun onConfirmPasswordChanged(value: String) {
        _confirmPassword.value = value
    }

    fun onCompanyNameChanged(value: String) {
        _companyName.value = value
    }

    fun onFullNameChanged(value: String) {
        _fullName.value = value
    }

    // 注册函数
    fun signUp() {
        _isLoading.value = true
        _errorMessage.value = null
        _successMessage.value = null

        viewModelScope.launch {
            val email = _email.value.trim()
            val password = _password.value
            val confirmPassword = _confirmPassword.value
            val fullName = _fullName.value.trim()
            val company = _companyName.value.trim()

            // 密码一致性检查
            if (password != confirmPassword) {
                _errorMessage.value = "Passwords do not match"
                _isLoading.value = false
                return@launch
            }

            // 密码强度校验
            if (password.length < 8 || password.none { it.isUpperCase() } || password.none { !it.isLetterOrDigit() }) {
                _errorMessage.value = "Password must have at least 8 characters, 1 uppercase letter, and 1 symbol"
                _isLoading.value = false
                return@launch
            }

            try {
                val response = supabase.gotrue.signUpWith(UserCredentials(email, password)) {
                    userMetadata = mapOf(
                        "full_name" to fullName,
                        "company" to company
                    )
                }
                _successMessage.value = "Account created successfully! Please verify your email."
            } catch (e: Exception) {
                _errorMessage.value = "Sign-up failed: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
