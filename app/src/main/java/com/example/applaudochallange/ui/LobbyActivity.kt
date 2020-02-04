package com.example.applaudochallange.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.applaudochallange.R
import com.example.applaudochallange.extentions.hideKeyboard
import com.example.applaudochallange.extentions.revealAnimation
import com.example.applaudochallange.extentions.revealClose
import com.example.applaudochallange.models.Data
import com.example.applaudochallange.ui.detailAnimeManga.DetailActivity
import com.example.applaudochallange.ui.filter.FilterFragment
import com.example.applaudochallange.ui.home.HomeFragment
import com.example.applaudochallange.utils.Constants
import kotlinx.android.synthetic.main.header.*
import org.koin.android.viewmodel.ext.android.viewModel



class LobbyActivity : AppCompatActivity() {

    private val lobbyViewModel by viewModel<LobbyViewModel>()
    private var fragmentMenuList: List<Fragment> = listOf(HomeFragment())
    private var currentFragment = fragmentMenuList[0]
    private var previousFragment: ArrayList<Fragment> = ArrayList()
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lobby_activity)
        if (savedInstanceState == null) {
            initNavigation()
            lobbyViewModel.requestAnimeMangaList("10")
        }

        searchButton.setOnClickListener {
         openSearch()
        }
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH) {
               if(searchEditText.text.toString().isNotEmpty()){
                   goToNextFragment(FilterFragment())
                   lobbyViewModel.requestSearchAnime(searchEditText.text.toString())
                   closeSearch()
                   return@setOnEditorActionListener true
               }
            }
            false
        }

        closeButton.setOnClickListener {
            closeSearch()
        }
    }

    private fun openSearch() {
        searchContainer.revealAnimation(bottomContainer)
        searchEditText.requestFocus()
        val imm =  getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
        isSearching = true
    }

    private fun initNavigation() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.nav_host_fragment, currentFragment)
        transaction.commit()
    }

    fun goToNextFragment(fragment: Fragment) {

        previousFragment.add(currentFragment)
        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, fragment)
            .commitNowAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
        currentFragment = fragment
    }

    private fun closeSearch() {
        searchContainer.revealClose()
        searchEditText.setText("")
        this.hideKeyboard()
    }


    override fun onBackPressed() {
        if (previousFragment.isNotEmpty()) {
            supportFragmentManager.beginTransaction()
                .hide(currentFragment)
                .show(previousFragment[previousFragment.size - 1])
                .commitNow()
            supportFragmentManager.executePendingTransactions()
            currentFragment = previousFragment[previousFragment.size - 1]
            previousFragment.removeAt(previousFragment.size - 1)
            if (isSearching) closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    fun goToItemDetail(item:Data, sharedImageView: ImageView){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.ITEM_EXTRA_DETAIL, item)
        intent.putExtra(
            item.attributes?.canonicalTitle,
            ViewCompat.getTransitionName(sharedImageView)
        )

        val options = ViewCompat.getTransitionName(sharedImageView)?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                it
            )
        }

        startActivity(intent, options?.toBundle())
    }
}





