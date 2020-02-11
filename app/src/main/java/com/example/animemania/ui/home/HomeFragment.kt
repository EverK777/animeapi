package com.example.animemania.ui.home


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.animemania.R
import com.example.animemania.extentions.configureRecycler
import com.example.animemania.models.Data
import com.example.animemania.ui.LobbyActivity
import com.example.animemania.ui.LobbyViewModel
import com.example.animemania.utils.DynamicAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_anime_manga.view.*
import kotlinx.android.synthetic.main.item_section_type.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class HomeFragment : NavHostFragment() {

    private val lobbyViewModel: LobbyViewModel by sharedViewModel()
    private val listSection: ArrayList<ArrayList<Data>> = ArrayList()
    private lateinit var adapterSection: DynamicAdapter<ArrayList<Data>>
    private val listAdapter: ArrayList<DynamicAdapter<Data>> = ArrayList()
    private val offsetList: ArrayList<Int> = ArrayList()
    private var positionScrolled = 0
    private var isLoading = false
    private lateinit var mainView: View
    private var itemClicked = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainView = inflater.inflate(R.layout.fragment_home, container, false)
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerSection()
        lobbyViewModel.getAnimeMangaList().observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty() && ::adapterSection.isInitialized) {
                listSection.addAll(it)
                adapterSection.notifyDataSetChanged()
                loadingLayout.visibility = View.GONE
            }else{

            }
        })
        lobbyViewModel.getNextPage().observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty() && !listAdapter.isNullOrEmpty()) {
                // notify item inserted when can scroll over
                for (i in 0 until it.size) {
                    listSection[positionScrolled].add(it[i])
                    listAdapter[positionScrolled].notifyItemInserted(listSection[positionScrolled].size)
                    recyclerSection[positionScrolled].findViewById<ProgressBar>(R.id.progressPagination)
                        .visibility = View.GONE
                    isLoading = false
                }
            }else {

            }
        })
    }


    private fun initRecyclerSection() {

        adapterSection = DynamicAdapter(
            layout = R.layout.item_section_type,
            entries = listSection,
            action = @SuppressLint("DefaultLocale")
            fun(viewHolder, view, item, position) {
                if (!item.isNullOrEmpty()) {
                    listAdapter.add(initRecyclerAnimeMangaList(item))
                    offsetList.add(10)
                    view.sectionTitle.text = item[0].type?.capitalize()
                    view.recyclerMangaAnime.setHasFixedSize(true)
                    view.recyclerMangaAnime.configureRecycler(false)
                    view.recyclerMangaAnime.adapter = listAdapter[position]
                    // scroll pagination
                    view.recyclerMangaAnime.addOnScrollListener(object :
                        RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            val visibleItemCount = view.recyclerMangaAnime.layoutManager?.childCount
                            val totalItemCount = listAdapter[position].itemCount
                            val firstVisibleItemPosition =
                                (view.recyclerMangaAnime.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                            if (dx > 0) {
                                if (visibleItemCount != null) {
                                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                                        view.progressPagination.visibility = View.VISIBLE
                                        positionScrolled = position
                                        lobbyViewModel.requestNextPage(
                                            item[position].type?:"anime",
                                            offsetList[position]
                                        )
                                        offsetList[position] += 10
                                    }
                                }
                            }
                        }

                    })

                }
            }
        )
        recyclerSection.setHasFixedSize(true)
        recyclerSection.configureRecycler(true)
        recyclerSection.adapter = adapterSection
    }

    private fun initRecyclerAnimeMangaList(data: List<Data>): DynamicAdapter<Data> {
        return DynamicAdapter(
            layout = R.layout.item_anime_manga,
            entries = data,
            action = fun(viewHolder, view, item, position) {
                view.animeMangaTitle.text = item.attributes?.canonicalTitle
                view.imageCover.load(item.attributes?.posterImage?.large)

                view.setOnClickListener {
                    if(!itemClicked){
                        itemClicked = true
                        ViewCompat.setTransitionName(view.imageCover, item.attributes?.canonicalTitle)
                        (activity as LobbyActivity).goToItemDetail(item,view.imageCover)
                        itemClicked = false
                    }
                }
            }
        )

    }


}
