package com.valentin.catsapi.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.valentin.catsapi.R
import com.valentin.catsapi.databinding.CatItemBinding
import com.valentin.catsapi.models.Cat

class CatViewHolder(
    private val binding: CatItemBinding,
    private val listener: CatListener,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(cat: Cat) {
        //binding.deleteButton
        Glide.with(binding.root.context)
            .load(cat.url)
            .placeholder(R.drawable.cat_placeholder)
            .into(binding.ivCat)



        binding.ivCat.setOnClickListener {
            binding.card.transitionName = cat.id
            listener.showDetailed(cat, binding.card)
        }

//        binding.ivLike.setOnClickListener {
//            listener.likeCat(cat)
//        }
//
//        binding.ivDownload.setOnClickListener {
//            listener.downloadImage(cat)
//        }
    }
}