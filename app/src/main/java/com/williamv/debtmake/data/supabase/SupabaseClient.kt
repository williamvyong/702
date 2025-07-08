package com.williamv.debtmake.data.supabase

import android.content.Context
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import com.williamv.debtmake.util.EnvUtil

object SupabaseClientProvider {

    @Volatile
    private var _client: SupabaseClient? = null

    fun getClient(context: Context): SupabaseClient {
        return _client ?: synchronized(this) {
            _client ?: run {
                val env = EnvUtil.readEnv(context)
                val url = env["SUPABASE_URL"] ?: throw Exception("SUPABASE_URL not found in .env")
                val anonKey = env["SUPABASE_ANON_KEY"] ?: throw Exception("SUPABASE_ANON_KEY not found in .env")
                createSupabaseClient(
                    supabaseUrl = url,
                    supabaseKey = anonKey
                ) {
                    install(Auth)
                    install(Postgrest)
                    install(Storage)
                }.also { _client = it }
            }
        }
    }
}