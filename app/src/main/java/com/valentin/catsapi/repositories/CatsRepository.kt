package com.valentin.catsapi.repositories

import com.valentin.catsapi.api.ApiHelper
import com.valentin.catsapi.models.Cat
import kotlin.random.Random

class CatsRepository(private val apiHelper: ApiHelper) {

    private val apiKey = "e3db1e1d-09ae-4f25-bb76-0b02052482ac"
//    private val limit = 20
//    private val order = "DESC"

    suspend fun loadCats(limit: Int, page: Int, order: String, type: String): List<Cat> {
        return apiHelper.loadCats(
            apiKey = apiKey,
            limit = limit,
            page = page ,
            order = order,
            type = type)
    }
}