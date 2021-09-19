package com.valentin.catsapi.state

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentin.catsapi.adapters.CatAdapter

class LoadState: LoadingState {
    override fun showState(adapter: CatAdapter, layoutManager: LinearLayoutManager, pos: Int) {
        Log.d("State", "Load state")
        adapter.showFooter()
        layoutManager.scrollToPosition(pos + 2)
    }
}