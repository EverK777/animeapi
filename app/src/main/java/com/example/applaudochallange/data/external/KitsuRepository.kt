package com.example.applaudochallange.data.external

import com.example.applaudochallange.data.external.kitsuApiService.CharacterResponse
import com.example.applaudochallange.data.external.kitsuApiService.KitsuApi
import com.example.applaudochallange.models.AnimeManga
import com.example.applaudochallange.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.*
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception


class KitsuRepository {


    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response? {
                val jsonObject = JSONObject()
                val request: Request = chain.request()
                val response: Response = chain.proceed(request)
                if (response.code() != 200) {
                    jsonObject.put("status", "ERROR")
                    val contentType = response.body()!!.contentType()
                    val body = ResponseBody.create(contentType, jsonObject.toString())
                    return response.newBuilder().body(body).build()
                }
                return response
            }
        })
        .build()

    private var kitsuApi: KitsuApi = Retrofit.Builder()
        .baseUrl(Constants.KITSU_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(KitsuApi::class.java)



     suspend fun fetchAnime(type : String, paginationNumber : String, paginationOffset : String) : List<AnimeManga>{

         val request = kitsuApi.getAnimeList(type, paginationNumber, paginationOffset)

         if(request.status == "ERROR") return emptyList()

         return if(request.data.isNullOrEmpty()) ArrayList() else request.data

    }


    suspend fun fetchRequestedAnimeSearch(type:String, name : String): List<AnimeManga>{

        val request = kitsuApi.getFilteredAnime(type, name)
        if(request.status == "ERROR") return emptyList()

        val listFiltered = request.data
        return if (listFiltered.isNullOrEmpty()) ArrayList() else listFiltered

        }

    suspend fun getCharactersList(type:String,idAnimeManga:String): List<AnimeManga>{
        val request = kitsuApi.getCharacters(type,idAnimeManga)
        val listCharacters = request.data

        if(request.status == "ERROR")return emptyList()

        return if (listCharacters.isNullOrEmpty()) ArrayList() else listCharacters

    }

    suspend fun getCharacterInfo(idCharacter:String?):CharacterResponse?{
        var request :CharacterResponse ?= null
        try{
             request = kitsuApi.getCharacter(idCharacter)
        }catch (e:Exception){

        }

        return if(request?.status == "ERROR") null else request
    }




}