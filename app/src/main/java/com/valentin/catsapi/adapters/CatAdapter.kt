package com.valentin.catsapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.valentin.catsapi.databinding.CatItemBinding
import com.valentin.catsapi.models.Cat

class CatAdapter(private val listener: CatListener)
    : ListAdapter<Cat, CatViewHolder>(itemComparator) {

    private var hasFooter = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatItemBinding.inflate(layoutInflater, parent, false)
        return CatViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
        listener.onCatBind(position)
    }

    override fun getItemCount(): Int {
        return if (hasFooter) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasFooter){
            if (isFooter(position)) FOOTER_ITEM else CAT_ITEM
        } else
            super.getItemViewType(position)
    }

    private fun isFooter(position: Int): Boolean {
        return if (hasFooter){
            return position == currentList.size
        }
        else
            false
    }


    private companion object {

        const val CAT_ITEM = 0
        const val FOOTER_ITEM = 1

        private val itemComparator = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }
}