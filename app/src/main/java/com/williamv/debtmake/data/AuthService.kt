// ✅ 文件位置：com.williamv.debtmake.data.AuthService.kt
package com.williamv.debtmake.data

/**
 * AuthService 是一个接口，定义用户认证相关的操作：
 * - 登录
 * - 注册
 * - 登出
 * - 检查当前是否已登录
 */
interface AuthService {

    /**
     * 使用邮箱和密码登录 Supabase
     */
    suspend fun login(email: String, password: String): Result<Unit>

    /**
     * 使用邮箱和密码注册 Supabase 用户
     */
    suspend fun register(email: String, password: String): Result<Unit>

    /**
     * 登出当前用户
     */
    suspend fun logout()

    /**
     * 检查是否已登录
     */
    fun isLoggedIn(): Boolean

    /**
     * 获取当前登录的用户 ID（user.id）
     */
    fun getCurrentUserId(): String?
}
