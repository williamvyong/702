package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.williamv.debtmake.data.repository.EntryRepository

/**
 * EntryViewModel 工厂，用于注入 EntryRepository
 */
class EntryViewModelFactory(
    private val entryRepository: EntryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntryViewModel(entryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
