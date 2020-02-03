package com.example.applaudochallange.ui.detailAnimeManga

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import coil.api.load
import com.example.applaudochallange.R
import com.example.applaudochallange.extentions.configureRecycler
import com.example.applaudochallange.models.AnimeManga
import com.example.applaudochallange.utils.Constants
import com.example.applaudochallange.utils.DynamicAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_anime_manga.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        backIcon.setOnClickListener {
            supportFinishAfterTransition()
        }


        val extras = intent.extras
        val item: AnimeManga? = extras?.getParcelable(Constants.ITEM_EXTRA_DETAIL)
        viewModel.requestCharacters(item?.type!!,item.id!!)

        viewModel.getCharacters().observe(this, Observer {
            loadingContainer.visibility = View.GONE
            if(!it.isNullOrEmpty()){
                configureRecyclerCharacters(it)
            }else {
                recyclerViewCharacters.visibility = View.GONE
            }
        })
        configView(item,extras)
    }

    private fun configureRecyclerCharacters(data: List<AnimeManga>) {

        val dynamicAdapter = DynamicAdapter(
            layout = R.layout.item_characters,
            entries = data,
            action = fun(_, view, item, _){
                view.imageCover.load(item.attributes?.image?.original)
                view.animeMangaTitle.text = item.attributes?.name

            }
        )

        recyclerViewCharacters.setHasFixedSize(true)
        recyclerViewCharacters.configureRecycler(false)
        recyclerViewCharacters.adapter = dynamicAdapter
    }

    private fun configView(item: AnimeManga?, extras : Bundle?) {
        if(item != null && extras !=null){
            val imageTransName = extras.getString(item.attributes?.canonicalTitle)
            imageCover.transitionName = imageTransName
            imageCover.load(item.attributes?.posterImage?.large)
            serieTv.text = item.attributes?.canonicalTitle
            coverCollapse.load(item.attributes?.coverImage?.large)
            synopsisText.text = item.attributes?.synopsis
            typeTV.text = item.type
            startDateTV.text = if(item.attributes?.startDate.isNullOrEmpty()) "-" else item.attributes?.startDate
            endDateTV.text = if(item.attributes?.endDate.isNullOrEmpty()) "-" else item.attributes?.endDate
            episodesTV.text = if(item.attributes?.episodeCount.isNullOrEmpty()) "-" else item.attributes?.episodeCount

        }
    }
}
