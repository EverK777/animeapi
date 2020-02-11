package com.example.animemania.data.external.kitsuApiService

import com.example.animemania.data.external.responses.BaseResponse
import com.example.animemania.data.external.responses.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KitsuApi {

    // url example https://kitsu.io/api/edge/anime?pageArrayList<Data>[limit]=1&page[offset]=0

    @GET("{type}")
    suspend fun getItemList(@Path("type")pathType:String,
                            @Query("page[limit]")pageNumber:String,
                            @Query("page[offset]")pageOffset:String) : BaseResponse

    @GET("{type}")
    suspend fun getFilterItem(@Path("type")pathType:String,
                              @Query("filter[text]")name:String) : BaseResponse

    @GET("{type}/{id}/relationships/{element}")
    suspend fun getElementsForId(@Path("type")pathType:String,
                                 @Path("id")idItem:String,@Path("element")element:String) : BaseResponse

    @GET("{type}/{id}/relationships/character")
    suspend fun getCharacterInfoId(@Path("type")type:String, @Path("id")id: String):CharacterResponse

    @GET("characters/{id}")
    suspend fun getAtributeInfoForId(@Path("id")characterId:String?) : CharacterResponse
}