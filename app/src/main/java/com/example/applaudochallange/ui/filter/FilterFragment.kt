package com.example.applaudochallange.ui.filter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import coil.api.load

import com.example.applaudochallange.R
import com.example.applaudochallange.extentions.configureRecycler
import com.example.applaudochallange.models.AnimeManga
import com.example.applaudochallange.ui.LobbyActivity
import com.example.applaudochallange.ui.LobbyViewModel
import com.example.applaudochallange.utils.DynamicAdapter
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.item_filtered.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FilterFragment : Fragment() {

    private val lobbyViewModel: LobbyViewModel by sharedViewModel()
    private val animeMangaFiltered : ArrayList<AnimeManga> = ArrayList()
    private lateinit var adapter : DynamicAdapter<AnimeManga>
    private var itemClicked = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initRecycler()
        lobbyViewModel.getFilter().observe(viewLifecycleOwner, Observer {
            if(it !=null){
                loadingLayout.visibility = View.GONE
                animeMangaFiltered.clear()
                animeMangaFiltered.addAll(it)
                adapter.notifyDataSetChanged()

            }
        })
        super.onActivityCreated(savedInstanceState)
    }

    private fun initRecycler(){
        adapter = DynamicAdapter(
            layout = R.layout.item_filtered,
            entries = animeMangaFiltered,
            action = fun(_, view, item, _) {
                view.imageCover.load(item.attributes.posterImage.large)
                view.nameSerieTV.text = item.attributes.canonicalTitle
                view.startDateTV.text = if(item.attributes.startDate.isNullOrEmpty()) "-" else item.attributes.startDate
                view.endDateTV.text = if(item.attributes.endDate.isNullOrEmpty()) "-" else item.attributes.endDate
                view.episodeNumberTV.text = if(item.attributes.episodeCount.isNullOrEmpty()) "-" else item.attributes.episodeCount
                view.statusTV.text = if(item.attributes.status.isNullOrEmpty()) "-" else item.attributes.status
                view.categoryTV.text = item.type

                view.setOnClickListener {
                    if(!itemClicked){
                        itemClicked = true
                        ViewCompat.setTransitionName(view.imageCover, item.attributes.canonicalTitle)
                        (activity as LobbyActivity).goToItemDetail(item,view.imageCover)
                        itemClicked = false
                    }

                }
            }
        )

        recyclerFilter.setHasFixedSize(true)
        recyclerFilter.configureRecycler(true)
        recyclerFilter.adapter = adapter

    }

}
