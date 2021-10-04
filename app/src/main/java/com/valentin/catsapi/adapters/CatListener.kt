package com.valentin.catsapi.adapters

import android.view.View
import android.widget.ImageView
import com.valentin.catsapi.models.Cat

interface CatListener {
    fun likeCat(cat: Cat)
    fun downloadImage(cat: Cat)
    fun showDetailed(cat: Cat, iv: View)
}