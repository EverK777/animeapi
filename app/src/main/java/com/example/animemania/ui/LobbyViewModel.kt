package com.example.animemania.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animemania.data.external.ApiResultHandle
import com.example.animemania.data.external.KitsuRepository
import com.example.animemania.models.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LobbyViewModel(private val repository: KitsuRepository) : ViewModel() {

    // first element of pair is anime and second manga
    private val dataList: MutableLiveData<ArrayList<ArrayList<Data>>> =
        MutableLiveData()
    private val dataPagination: MutableLiveData<ArrayList<Data>> = MutableLiveData()
    private val filteredItem: MutableLiveData<ArrayList<Data>> = MutableLiveData()


    fun requestAnimeMangaList(
        numberOfElements: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val animeFetched = repository.fetchAnime("anime", numberOfElements, "0")
            val mangaFetched = repository.fetchAnime("manga", numberOfElements, "0")

            val animeList: ArrayList<Data> = ArrayList()
            val mangaList: ArrayList<Data> = ArrayList()

            when (animeFetched) {
                is ApiResultHandle.Success -> animeFetched.value.data?.let { animeList.addAll(it) }
            }

            when (mangaFetched) {
                is ApiResultHandle.Success -> mangaFetched.value.data?.let { mangaList.addAll(it) }
            }


            val dataUnion: ArrayList<ArrayList<Data>> = ArrayList()

            dataUnion.add(animeList)
            dataUnion.add((mangaList))

            dataList.postValue(dataUnion)
        }
    }

    fun getAnimeMangaList(): LiveData<ArrayList<ArrayList<Data>>> {
        return dataList
    }

    fun requestNextPage(type: String, offsetCount: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            val dataFetched: ArrayList<Data> = ArrayList()
            val repositoryResponse =
                repository.fetchAnime(type, "10", offsetCount.toString())
            when (repositoryResponse) {
                is ApiResultHandle.Success -> repositoryResponse.value.data?.let {
                    dataFetched.addAll(
                        it
                    )
                }

            }


            val dataLst: ArrayList<Data> = ArrayList()

            dataLst.addAll(dataFetched)
            dataPagination.postValue(dataLst)
        }
    }

    fun getNextPage(): LiveData<ArrayList<Data>> {
        return dataPagination
    }


    fun requestSearchAnime(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val animeRequest = repository.fetchRequestedAnimeSearch("anime", name)
            val mangaRequest = repository.fetchRequestedAnimeSearch("manga", name)

            val animeMatched : ArrayList<Data> = ArrayList()
            val mangaMatched : ArrayList<Data> = ArrayList()

            when(animeRequest){
                is ApiResultHandle.Success -> animeRequest.value.data?.let { animeMatched.addAll(it) }
            }

            when(mangaRequest){
                is ApiResultHandle.Success -> mangaRequest.value.data?.let { mangaMatched.addAll(it) }
            }

            val dataUnion: ArrayList<Data> = ArrayList()

            dataUnion.addAll(animeMatched)
            dataUnion.addAll(mangaMatched)

            filteredItem.postValue(dataUnion)

        }
    }

    fun getFilter(): LiveData<ArrayList<Data>> {
        return filteredItem
    }


}