package com.example.animemania.ui.detailAnimeManga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animemania.data.external.ApiResultHandle
import com.example.animemania.data.external.KitsuRepository
import com.example.animemania.models.Data
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: KitsuRepository) : ViewModel() {

    private val charactersData: MutableLiveData<ArrayList<Data>> = MutableLiveData()


    fun requestCharacters(type: String, id: String) {
        viewModelScope.launch {
            val charactersToSend : ArrayList<Data> = ArrayList()
            //request list of characters
            val charactersList = repository.getElementListForId(type, id, "$type-characters")
            if(charactersList is ApiResultHandle.Success) {
                charactersList.value.data?.forEach {
                    // request character id
                    val characterId = repository.getCharacterId("$type-characters",it.id?:"")
                    if(characterId is ApiResultHandle.Success) {
                        // request character info
                        val characterInfoResponse = repository.getCharacterInfo(characterId.value.data.id)
                        if(characterInfoResponse is ApiResultHandle.Success){
                            charactersToSend.add(characterInfoResponse.value.data)
                        }
                    }
                }
                charactersData.postValue(charactersToSend)
            }else{
                charactersData.postValue(charactersToSend)
            }
        }
    }


    fun getCharacters(): LiveData<ArrayList<Data>> {
        return charactersData
    }

}