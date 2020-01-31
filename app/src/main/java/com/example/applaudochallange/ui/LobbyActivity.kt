package com.example.applaudochallange.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.applaudochallange.R
import com.example.applaudochallange.extentions.revealAnimation
import com.example.applaudochallange.extentions.revealClose
import com.example.applaudochallange.ui.home.HomeFragment
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.lobby_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class LobbyActivity : AppCompatActivity() {

    private val lobbyViewModel by viewModel<LobbyViewModel>()
    private lateinit var navController : NavController
    private var fragmentMenuList: List<Fragment> = listOf(HomeFragment())
    private var currentFragment = fragmentMenuList[0]
    private var previousFragment : ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lobby_activity)
        changeFragment(currentFragment)
        lobbyViewModel.requestAnimeMangaList("10")
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        searchButton.setOnClickListener {
            searchContainer.revealAnimation(bottomContainer)


        }
        closeButton.setOnClickListener {
            searchContainer.revealClose()


        }
    }

     fun changeFragment (fragment : Fragment)
    {
        previousFragment.add(currentFragment)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.nav_host_fragment, fragment)
            .commit()
        currentFragment = fragment
    }

    fun pop(){
        supportFragmentManager
            .beginTransaction()
            .remove(currentFragment)
            .commit()
    }

}
