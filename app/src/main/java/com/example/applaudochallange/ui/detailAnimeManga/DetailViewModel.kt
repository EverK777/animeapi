package com.example.applaudochallange.ui.detailAnimeManga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applaudochallange.data.external.KitsuRepository
import com.example.applaudochallange.models.AnimeManga
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: KitsuRepository) : ViewModel() {

    private val charactersData: MutableLiveData<ArrayList<AnimeManga>> = MutableLiveData()


    fun requestCharacters(type: String, id: String) {
        viewModelScope.launch {
            val characters: ArrayList<AnimeManga> = ArrayList()
            val listCharacters = repository.getCharactersList(type, id)
            viewModelScope.launch {
                for (i in listCharacters.indices) {
                    viewModelScope.launch {
                        val character = repository.getCharacterInfo(listCharacters[i].id)
                        character?.data
                        if (!character?.data?.id .isNullOrEmpty()) {
                            if (character != null) {
                                characters.add(character.data)
                            }
                        }
                    }.join()
                }
                charactersData.postValue(characters)
            }
        }
    }

    fun getCharacters(): LiveData<ArrayList<AnimeManga>> {
        return charactersData
    }

}