package com.example.applaudochallange.data.external

import com.example.applaudochallange.data.external.kitsuApiService.KitsuApi
import com.example.applaudochallange.models.AnimeManga
import com.example.applaudochallange.utils.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KitsuRepository {

    private var kitsuApi: KitsuApi = Retrofit.Builder()
        .baseUrl(Constants.KITSU_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(KitsuApi::class.java)



     suspend fun fetchAnime(paginationNumber : String, paginationOffset : String) : List<AnimeManga>{
        return kitsuApi.getAnimeList(paginationNumber,paginationOffset).animeMangaList
    }

     suspend fun fetchManga(paginationNumber : String, paginationOffset : String) : List<AnimeManga>{
        return kitsuApi.getMangaList(paginationNumber,paginationOffset).animeMangaList
    }

    suspend fun fetchRequestedAnimeSearch(name : String): List<AnimeManga>{
        return kitsuApi.getFilteredAnime(name).animeMangaList
    }

    suspend fun fetchRequestedMangaSearch(name : String): List<AnimeManga>{
        return kitsuApi.getFilteredManga(name).animeMangaList
    }

}