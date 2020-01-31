package com.example.applaudochallange.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.applaudochallange.R
import org.koin.android.viewmodel.ext.android.viewModel

class LobbyActivity : AppCompatActivity() {

    private val lobbyViewModel by viewModel<LobbyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lobby_activity)

        lobbyViewModel.requestAnimeMangaList("10")
    }
}
