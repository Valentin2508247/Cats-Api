package com.valentin.catsapi.adapters

import androidx.recyclerview.widget.RecyclerView
import com.valentin.catsapi.databinding.FooterBinding

class FooterViewHolder(
    private val binding: FooterBinding,
    private val listener: CatListener,
): RecyclerView.ViewHolder(binding.root)
