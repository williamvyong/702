package com.williamv.debtmake.util

import android.content.Context
import android.content.SharedPreferences

object RecentBookStore {
    private const val PREF_NAME = "recent_book_prefs"
    private const val KEY_BOOK_ID = "book_id"

    fun setRecentBookId(context: Context, bookId: Long) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putLong(KEY_BOOK_ID, bookId).apply()
    }

    fun getRecentBookId(context: Context): Long? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val id = prefs.getLong(KEY_BOOK_ID, -1L)
        return if (id != -1L) id else null
    }
}
