package com.valentin.catsapi.state

import com.valentin.catsapi.databinding.FragmentCatsBinding

interface LoadingState {
    fun showState(binding: FragmentCatsBinding)

    companion object {
        val Static = StaticState()
        val Loading = LoadState()
        val Error = ErrorState()
    }
}