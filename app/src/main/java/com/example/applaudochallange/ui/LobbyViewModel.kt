package com.example.applaudochallange.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applaudochallange.data.external.KitsuRepository
import com.example.applaudochallange.models.AnimeManga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LobbyViewModel (private val repository: KitsuRepository) : ViewModel() {

    // first element of pair is anime and second manga
    private val animeMangaList : MutableLiveData<ArrayList<ArrayList<AnimeManga>>> = MutableLiveData()
    private val animeMangaPagination : MutableLiveData<ArrayList<AnimeManga>> = MutableLiveData()



    fun requestAnimeMangaList(numberOfElements : String){
        viewModelScope.launch(Dispatchers.IO) {
           val animeFetched = repository.fetchAnime(numberOfElements, "0")
           val mangaFetched= repository.fetchManga(numberOfElements, "0")

           val animeList: ArrayList<AnimeManga> = ArrayList()
           val mangaList: ArrayList<AnimeManga> = ArrayList()

            animeList.addAll(animeFetched)
            mangaList.addAll(mangaFetched)


          val dataUnion : ArrayList<ArrayList<AnimeManga>> = ArrayList()

            dataUnion.add(animeList)
            dataUnion.add((mangaList))

            animeMangaList.postValue(dataUnion)
        }
    }

    fun getAnimeMangaList() : LiveData<ArrayList<ArrayList<AnimeManga>>>{
        return animeMangaList
    }

    fun requestNextPage(type:Int,offsetCount:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val animeMangaFetched =
            if(type == 0) {
                repository.fetchAnime("10", offsetCount.toString())
            }else {
                repository.fetchManga("10", offsetCount.toString())
            }

            val animeMangaLst: ArrayList<AnimeManga> = ArrayList()

            animeMangaLst.addAll(animeMangaFetched)
            animeMangaPagination.postValue(animeMangaLst)

        }
    }

    fun getNextPage() : LiveData<ArrayList<AnimeManga>>{
        return animeMangaPagination
    }


}