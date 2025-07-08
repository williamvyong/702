package com.williamv.debtmake.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * AuthService 实现类，对接 Supabase 3.2.0，负责登录/注册/登出等功能
 */
class AuthServiceImpl(
    private val client: SupabaseClient
) : AuthService {

    // 登录，返回 UserSession
    override suspend fun login(email: String, password: String): UserSession? = withContext(Dispatchers.IO) {
        try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            // 登录后获取当前会话
            client.auth.currentSessionOrNull()
        } catch (e: RestException) {
            null
        }
    }

    // 注册，返回 UserSession
    override suspend fun register(email: String, password: String): UserSession? = withContext(Dispatchers.IO) {
        try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            // 注册后获取当前会话
            client.auth.currentSessionOrNull()
        } catch (e: RestException) {
            null
        }
    }

    // 登出
    override suspend fun logout() {
        client.auth.signOut()
    }

    // 是否已登录
    override fun isLoggedIn(): Boolean {
        return client.auth.currentSessionOrNull() != null
    }

    // 获取当前用户ID
    override fun getCurrentUserId(): String? {
        return client.auth.currentUserOrNull()?.id
    }
}