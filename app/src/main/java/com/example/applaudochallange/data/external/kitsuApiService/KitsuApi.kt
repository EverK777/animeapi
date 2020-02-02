package com.example.applaudochallange.data.external.kitsuApiService

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
    @GET("anime")
    suspend fun getFilteredAnime(@Query("filter[text]")name:String) : BaseResponse

    @GET("manga")
    suspend fun getFilteredManga(@Query("filter[text]")name:String) : BaseResponse
}