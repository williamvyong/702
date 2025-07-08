// ✅ 文件位置：com.williamv.debtmake.data.AuthServiceImpl.kt
package com.williamv.debtmake.data

import io.github.jan.supabase.exceptions.AuthException
import io.github.jan.supabase.gotrue.user.UserSession
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Email
import com.williamv.debtmake.supabase.SupabaseClientProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * AuthServiceImpl 实现了 AuthService 接口，
 * 使用 Supabase 实现用户认证逻辑。
 */
class AuthServiceImpl : AuthService {

    private val client = SupabaseClientProvider.client
    private val auth = client.auth

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
            }
            Result.success(Unit)
        } catch (e: AuthException) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
            }
            Result.success(Unit)
        } catch (e: AuthException) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            auth.signOut()
        }
    }

    override fun isLoggedIn(): Boolean {
        return auth.currentSessionOrNull() != null
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUserOrNull()?.id
    }
}
