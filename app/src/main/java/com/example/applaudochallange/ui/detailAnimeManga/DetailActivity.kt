package com.example.applaudochallange.ui.detailAnimeManga

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.example.applaudochallange.R
import com.example.applaudochallange.models.AnimeManga
import com.example.applaudochallange.utils.Constants
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        backIcon.setOnClickListener {
            supportFinishAfterTransition()
        }


        val extras = intent.extras
        val item: AnimeManga? = extras?.getParcelable(Constants.ITEM_EXTRA_DETAIL)
        configView(item,extras)
    }

    private fun configView(item: AnimeManga?, extras : Bundle?) {
        if(item != null && extras !=null){
            val imageTransName = extras.getString(item.attributes.canonicalTitle)
            imageCover.transitionName = imageTransName
            imageCover.load(item.attributes.posterImage.large)
            serieTv.text = item.attributes.canonicalTitle
            coverCollapse.load(item.attributes.coverImage?.large)
            synopsisText.text = item.attributes.synopsis
            typeTV.text = item.type
            startDateTV.text = if(item.attributes.startDate.isNullOrEmpty()) "-" else item.attributes.startDate
            endDateTV.text = if(item.attributes.endDate.isNullOrEmpty()) "-" else item.attributes.endDate
            episodesTV.text = if(item.attributes.episodeCount.isNullOrEmpty()) "-" else item.attributes.episodeCount

        }
    }
}
