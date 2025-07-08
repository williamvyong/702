package com.williamv.debtmake.data

import io.github.jan.supabase.auth.user.UserSession

interface AuthService {
    suspend fun login(email: String, password: String): UserSession?
    suspend fun register(email: String, password: String): UserSession?
    suspend fun logout()
    fun isLoggedIn(): Boolean
    fun getCurrentUserId(): String?
}
