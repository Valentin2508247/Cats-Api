package com.valentin.catsapi.state

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentin.catsapi.adapters.CatAdapter

class ErrorState: LoadingState {

    override fun showState(adapter: CatAdapter, layoutManager: LinearLayoutManager, pos: Int) {
        Log.d("State", "Error state")
        adapter.showError("Loading error")
        layoutManager.scrollToPosition(pos + 1)
    }
}
