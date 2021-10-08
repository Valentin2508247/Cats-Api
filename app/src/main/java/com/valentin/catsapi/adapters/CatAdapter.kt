package com.valentin.catsapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.valentin.catsapi.databinding.CatItemBinding
import com.valentin.catsapi.databinding.ErrorBinding
import com.valentin.catsapi.databinding.FooterBinding
import com.valentin.catsapi.models.Cat

class CatAdapter(private val listener: CatListener): ListAdapter<Cat, RecyclerView.ViewHolder>(itemComparator) {

    private var hasFooter = false
    private var hasError = false
    private var message = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            FOOTER_ITEM -> {
                val binding = FooterBinding.inflate(layoutInflater, parent, false)
                FooterViewHolder(binding, listener)
            }
            CAT_ITEM -> {
                val binding = CatItemBinding.inflate(layoutInflater, parent, false)
                CatViewHolder(binding, listener)
            }
            ERROR_ITEM -> {
                val binding = ErrorBinding.inflate(layoutInflater, parent, false)
                ErrorViewHolder(binding, listener)
            }
            else -> {
                val binding = CatItemBinding.inflate(layoutInflater, parent, false)
                CatViewHolder(binding, listener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CatViewHolder -> {
                holder.bind(getItem(position))
            }
            is FooterViewHolder -> {
                //
            }
            is ErrorViewHolder -> {
                holder.bind(message)
            }
        }
    }

    override fun getItemCount(): Int {
        if (hasFooter)
            return super.getItemCount() + 1
        if (hasError)
            return super.getItemCount() + 1
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        if (hasFooter) {
            return if (isFooter(position)) FOOTER_ITEM else CAT_ITEM
        }
        if (hasError)
            return if (isError(position)) ERROR_ITEM else CAT_ITEM

        return CAT_ITEM
    }

    private fun isFooter(position: Int): Boolean {
        return if (hasFooter) {
            return position == currentList.size
        } else false
    }

    fun hideFooter() {
        if (!hasFooter)
            return
        hasFooter = false
        notifyItemRemoved(currentList.size)
    }

    fun showFooter() {
        hideError()
        if (hasFooter)
            return
        hasFooter = true
        notifyItemInserted(currentList.size)
    }

    private fun isError(position: Int): Boolean {
        return if (hasError) {
            return position == currentList.size
        } else false
    }

    private fun hideError() {
        if (!hasError)
            return
        hasError = false
        notifyItemRemoved(currentList.size)
    }

    fun showError(message: String) {
        hideFooter()
        this.message = message
        hasError = true
        notifyItemInserted(currentList.size)
    }

    private companion object {

        const val CAT_ITEM = 0
        const val FOOTER_ITEM = 1
        const val ERROR_ITEM = 2

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
