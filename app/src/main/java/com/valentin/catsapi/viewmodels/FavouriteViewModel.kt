package com.valentin.catsapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valentin.catsapi.models.Cat
import com.valentin.catsapi.repositories.CatsRepository
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(private val repository: CatsRepository): ViewModel() {
    val cats = repository.localCats()

    fun saveCat(cat: Cat) {
        repository.saveCat(cat)
    }

    private companion object {
        const val TAG = "FavouriteViewModel"
    }
}

class FavouriteViewModelFactory @Inject constructor(private val repository: CatsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
