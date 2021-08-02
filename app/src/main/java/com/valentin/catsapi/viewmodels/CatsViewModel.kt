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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CatsViewModel(private val repository: CatsRepository): ViewModel() {
    private val TAG = "CatsViewModel"

    val cats = MutableLiveData<List<Cat>>()
    val state = MutableLiveData<LoadingState>()

    init {
        state.value = LoadingState.Static
        //loadCats()
    }

    var page = 0


    fun loadCats() {
        state.value = LoadingState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            cats.postValue(repository.loadCats(limit, page++, order, type))
            Log.d(TAG, cats.value.toString())
        }
    }

    private companion object {
        const val limit = 20
        const val order = "DESC"
        const val type = "jpg,png"
    }
}

class CatsViewModelFactory(private val repository: CatsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}