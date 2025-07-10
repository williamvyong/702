package com.williamv.debtmake.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.recentBookDataStore by preferencesDataStore("recent_book_store")

object RecentBookStore {
    private val RECENT_BOOK_ID_KEY = stringPreferencesKey("recent_book_id")

    suspend fun saveRecentBookId(context: Context, bookId: String) {
        context.recentBookDataStore.edit { prefs ->
            prefs[RECENT_BOOK_ID_KEY] = bookId
        }
    }

    suspend fun getRecentBookId(context: Context): String? {
        return context.recentBookDataStore.data
            .map { prefs -> prefs[RECENT_BOOK_ID_KEY] }
            .first()
    }
}
