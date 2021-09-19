package com.valentin.catsapi.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.valentin.catsapi.models.Cat
import com.valentin.catsapi.repositories.CatsRepository
import com.valentin.catsapi.state.LoadingState
import com.valentin.catsapi.state.StaticState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


class CatsViewModel @Inject constructor(private val repository: CatsRepository): ViewModel() {
    private val TAG = "CatsViewModel"


    private val exceptionHandler = CoroutineExceptionHandler {
        _, throwable -> handleError(throwable)
    }

    val cats = MutableLiveData<MutableList<Cat>>()
    val state = MutableLiveData<LoadingState>()
    private val currentList: MutableList<Cat> = mutableListOf<Cat>()
    var page = 0
    var pos = 0

    init {
        state.value = LoadingState.Static
        loadCats()
    }




    fun loadCats() {
        state.value = LoadingState.Loading
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val list = repository.loadCats(limit, page, order, type)
            pos = page * limit
            currentList.addAll(list)
            cats.postValue(currentList)
            page++
            Log.d(TAG, cats.value.toString())
            state.postValue(LoadingState.Static)
        }
    }

    fun saveCat(cat: Cat) {
        repository.saveCat(cat)
    }

    private fun handleError(t: Throwable) {
        Log.e(TAG, "exception!", t)
        state.postValue(LoadingState.Error)
    }

    private companion object {
        const val limit = 8
        const val order = "DESC"
        const val type = "jpg,png"
    }
}

class CatsViewModelFactory @Inject constructor(private val repository: CatsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}