package com.peekster.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.no_movie_found.view.*
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import com.couchbase.lite.MutableDocument
import com.peekster.Interfaces.MovieListiner
import com.peekster.Player_Video
import com.peekster.adapter.Category_adapter
import com.peekster.adapter.Movie_Category_Adapter
import com.peekster.adapter.Movie_Grid_Adapter
import com.peekster.database.movie.database_utilss
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlinx.android.synthetic.main.movies_grid.view.*


class Movies_Grid() : Fragment() {
    
    val TAG="MOVIE"
    lateinit var adapter:Movie_Grid_Adapter
    lateinit var Category:String;
    lateinit var MovieListiner:MovieListiner;


    constructor(s:String,listiner: MovieListiner) : this() {
        Category=s
        MovieListiner=listiner
        Log.e(TAG+".constructor","set category "+s)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        var v:View=inflater.inflate(com.peekster.R.layout.movies_grid, container, false)


        adapter= Movie_Grid_Adapter(context!!).setCategory(Category)
        var lm:LinearLayoutManager =LinearLayoutManager(context)
        lm.orientation=LinearLayoutManager.VERTICAL
        v.movies_grid.adapter=adapter
        v.movies_grid.layoutManager=lm

        return v
    }




}