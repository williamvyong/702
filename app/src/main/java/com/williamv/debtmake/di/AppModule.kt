// ✅ 文件位置：com.williamv.debtmake.di.AppModule.kt
package com.williamv.debtmake.di

import android.app.Application
import android.content.Context
import com.williamv.debtmake.data.AuthService
import com.williamv.debtmake.data.AuthServiceImpl
import com.williamv.debtmake.data.supabase.SupabaseClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

/**
 * AppModule 是 Hilt 的模块类，提供全局依赖注入的对象。
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * 提供 Supabase 客户端单例，供全局使用
     */
    @Provides
    @Singleton
    fun provideSupabaseClient(@ApplicationContext context: Context): SupabaseClient {
        return SupabaseClientProvider.getClient(context)
    }

    /**
     * 提供 AuthService 的实现类（用于登录注册）
     */
    @Provides
    @Singleton
    fun provideAuthService(client: SupabaseClient): AuthService {
        return AuthServiceImpl(client)
    }
}