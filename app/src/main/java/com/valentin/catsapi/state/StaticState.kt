package com.valentin.catsapi.state

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.valentin.catsapi.adapters.CatAdapter
import com.valentin.catsapi.databinding.FragmentCatsBinding

class StaticState: LoadingState {
    override fun showState(adapter: CatAdapter, layoutManager: LinearLayoutManager, pos: Int) {
        Log.d("State", "Static state")
        adapter.hideFooter()
        layoutManager.scrollToPosition(pos)
    }
}