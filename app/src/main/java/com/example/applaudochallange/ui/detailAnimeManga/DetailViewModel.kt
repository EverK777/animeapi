package com.example.applaudochallange.ui.detailAnimeManga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applaudochallange.data.external.ApiResultHandle
import com.example.applaudochallange.data.external.KitsuRepository
import com.example.applaudochallange.models.Data
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: KitsuRepository) : ViewModel() {

    private val charactersData: MutableLiveData<ArrayList<Data>> = MutableLiveData()


    fun requestCharacters(type: String, id: String) {
        val characters: ArrayList<Data> = ArrayList()
        viewModelScope.launch {
            val request = repository.getElementListForId(type, id, "characters")
            if (request is ApiResultHandle.Success) request.value.data?.let {
                characters.addAll(it)
            }
            viewModelScope.launch {
                characters.forEach {
                    val characterInfoResponse = repository.getCharacterInfo(it.id)
                    if (characterInfoResponse is ApiResultHandle.Success) it.attributes =
                        characterInfoResponse.value.data.attributes
                }
                charactersData.postValue(characters)
            }.join()
        }
    }


    fun getCharacters(): LiveData<ArrayList<Data>> {
        return charactersData
    }

}