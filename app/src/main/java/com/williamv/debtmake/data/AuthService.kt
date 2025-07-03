package com.williamv.debtmake.data

class AuthService {
    suspend fun signIn(email: String, password: String): Boolean {
        // TODO: 后面接入 Supabase GoTrue
        return email == "test@example.com" && password == "123456"
    }
}