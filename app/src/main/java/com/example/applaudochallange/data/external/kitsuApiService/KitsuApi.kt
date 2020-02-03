package com.example.applaudochallange.data.external.kitsuApiService

import com.example.applaudochallange.models.AnimeManga
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KitsuApi {

    // url example https://kitsu.io/api/edge/anime?page[limit]=1&page[offset]=0

    @GET("{type}")
    suspend fun getAnimeList(@Path("type")pathType:String,
                             @Query("page[limit]")pageNumber:String,
                               @Query("page[offset]")pageOffset:String) : BaseResponse

    @GET("{type}")
    suspend fun getFilteredAnime(@Path("type")pathType:String,
                                 @Query("filter[text]")name:String) : BaseResponse

    @GET("{type}/{id}/relationships/characters")
    suspend fun getCharacters(@Path("type")pathType:String,
                              @Path("id")idItem:String) : BaseResponse

    @GET("characters/{idCharacter}")
    suspend fun getCharacter(@Path("idCharacter")characterId:String?) : CharacterResponse
}