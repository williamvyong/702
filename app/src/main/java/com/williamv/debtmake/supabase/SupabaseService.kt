// ✅ 文件位置：com.williamv.debtmake.supabase.SupabaseClientProvider.kt
package com.williamv.debtmake.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.AuthConfig
import io.github.jan.supabase.auth.providers.Email
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import com.williamv.debtmake.BuildConfig

/**
 * SupabaseClientProvider 是一个单例类
 * 用于初始化 Supabase 客户端连接，并在应用中复用
 */
object SupabaseClientProvider {

    // 初始化 SupabaseClient 单例
    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY
        ) {
            // 启用你需要的模块
            install(Auth) {
                // 配置邮箱密码登录
                authConfig = AuthConfig {
                    autoRefreshToken = true
                    persistSession = true
                }
            }
            install(Postgrest) // 启用 SQL 数据服务
            install(Storage)   // 启用 Supabase 文件存储服务（备用）
        }
    }
}
