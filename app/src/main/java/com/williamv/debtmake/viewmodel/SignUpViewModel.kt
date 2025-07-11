package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpViewModel : ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val companyName = MutableStateFlow("")
    val fullName = MutableStateFlow("")
    val isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")
    val successMessage = MutableStateFlow("")

    fun onEmailChanged(value: String) { email.value = value }
    fun onPasswordChanged(value: String) { password.value = value }
    fun onConfirmPasswordChanged(value: String) { confirmPassword.value = value }
    fun onCompanyNameChanged(value: String) { companyName.value = value }
    fun onFullNameChanged(value: String) { fullName.value = value }

    fun signUp() {
        isLoading.value = true
        // 实际注册逻辑/校验/请求
        // ...成功
        successMessage.value = "Sign up success"
        isLoading.value = false
        // ...失败
        // errorMessage.value = "Failed because ..."
        // isLoading.value = false
    }
}
