package com.example.applaudochallange.data.external.kitsuApiService

import com.example.applaudochallange.models.AnimeManga
import retrofit2.http.GET
import retrofit2.http.Query

interface KitsuApi {

    // url example https://kitsu.io/api/edge/anime?page[limit]=1&page[offset]=0

    @GET("anime")
    suspend fun getAnimeList(@Query("page[limit]")pageNumber:String,
                               @Query("page[offset]")pageOffset:String) : BaseResponse
    @GET("manga")
    suspend fun getMangaList(@Query("page[limit]")pageNumber:String,
                             @Query("page[offset]")pageOffset:String) : BaseResponse
}