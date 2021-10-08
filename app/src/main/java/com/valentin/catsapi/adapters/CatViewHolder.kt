package com.valentin.catsapi.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.valentin.catsapi.R
import com.valentin.catsapi.activities.MainActivity
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
            val location = IntArray(2)
            it.getLocationOnScreen(location)
            val x = location[0]
            val y = location[1]
            Log.d(TAG, "x: $x, y: $y")
            binding.ivCat.transitionName = cat.id
            Log.d(TAG, "name: ${it.transitionName}")
            listener.showDetailed(cat, binding.ivCat)
        }

//        binding.ivLike.setOnClickListener {
//            listener.likeCat(cat)
//        }
//
//        binding.ivDownload.setOnClickListener {
//            listener.downloadImage(cat)
//        }
    }

    private companion object {
        const val TAG = "CatViewHolder"
    }
}
