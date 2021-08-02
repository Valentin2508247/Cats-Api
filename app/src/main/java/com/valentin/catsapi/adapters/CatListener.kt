package com.valentin.catsapi.adapters

import com.valentin.catsapi.models.Cat

interface CatListener {
    fun likeCat(cat: Cat)
    fun downloadImage(cat: Cat)
    fun onCatBind(pos: Int)
}