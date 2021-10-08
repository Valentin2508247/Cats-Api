package com.valentin.catsapi.api

import com.valentin.catsapi.models.Cat

class ApiHelper(private val catApi: CatApi) {

    suspend fun loadCats(apiKey: String, limit: Int, page: Int, order: String, type: String): List<Cat> {
        return catApi.loadCats(apiKey, limit, page, order, type)
    }
}
