package com.valentin.catsapi.state

import androidx.recyclerview.widget.LinearLayoutManager
import com.valentin.catsapi.adapters.CatAdapter

interface LoadingState {
    fun showState(adapter: CatAdapter, layoutManager: LinearLayoutManager, pos: Int)

    companion object {
        val Static = StaticState()
        val Loading = LoadState()
        val Error = ErrorState()
    }
}
