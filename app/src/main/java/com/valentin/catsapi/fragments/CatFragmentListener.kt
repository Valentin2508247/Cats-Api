package com.valentin.catsapi.fragments

import android.view.View
import com.valentin.catsapi.models.Cat

interface CatFragmentListener {
    fun downloadImage(url: String)
    fun showDetailed(cat: Cat, iv: View)
}
