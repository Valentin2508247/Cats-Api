package com.valentin.catsapi.api

import com.valentin.catsapi.models.Cat
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatApi {
    @GET("v1/images/search")
    suspend fun loadCats(
        @Header("x-api-key") key: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("order") order: String,
        @Query("mime_types") type: String
    ): List<Cat>
}
