package com.example.animemania.data.external

import com.example.animemania.data.external.kitsuApiService.KitsuApi
import com.example.animemania.data.external.responses.BaseResponse
import com.example.animemania.data.external.responses.CharacterResponse
import com.example.animemania.data.external.responses.ErrorResponse
import com.example.animemania.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class KitsuRepository {


    private var kitsuApi: KitsuApi = Retrofit.Builder()
        .baseUrl(Constants.KITSU_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(KitsuApi::class.java)




     suspend fun fetchAnime(type : String, paginationNumber : String, paginationOffset : String) : ApiResultHandle<BaseResponse>{
         return safeApiRequest(Dispatchers.IO) {kitsuApi.getItemList(type,paginationNumber,paginationOffset)}
     }


    suspend fun fetchRequestedAnimeSearch(type:String, name : String): ApiResultHandle<BaseResponse>{
        return safeApiRequest(Dispatchers.IO) {kitsuApi.getFilterItem(type,name)}

    }

    suspend fun getElementListForId(type:String, id:String, element:String) : ApiResultHandle<BaseResponse>{
        return safeApiRequest(Dispatchers.IO) {kitsuApi.getElementsForId(type,id,element)}
    }

    suspend fun getCharacterId(type:String, id:String) : ApiResultHandle<CharacterResponse>{
        return safeApiRequest(Dispatchers.IO) {kitsuApi.getCharacterInfoId(type,id)}
    }

    suspend fun getCharacterInfo(idCharacter:String?): ApiResultHandle<CharacterResponse>{
        return  safeApiRequest(Dispatchers.IO) {kitsuApi.getAtributeInfoForId(idCharacter)}
    }


    private suspend fun <T> safeApiRequest(dispatcher: CoroutineDispatcher, apiRequest: suspend () -> T): ApiResultHandle<T> {
        return withContext(dispatcher) {
            try {
                ApiResultHandle.Success(apiRequest.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ApiResultHandle.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        ApiResultHandle.ApiError(code.toString(), errorResponse)
                    }
                    else -> {
                        ApiResultHandle.ApiError("", null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                Gson().fromJson<ErrorResponse>(it.readUtf8(),ErrorResponse::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }
}