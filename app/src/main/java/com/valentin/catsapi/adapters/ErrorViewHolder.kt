package com.valentin.catsapi.adapters

import androidx.recyclerview.widget.RecyclerView
import com.valentin.catsapi.databinding.ErrorBinding


class ErrorViewHolder(
    private val binding: ErrorBinding,
    private val listener: CatListener,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(message: String) {
        binding.tvError.text = message
    }
}