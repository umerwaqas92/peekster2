package com.peekster.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.no_movie_found.view.*
import android.content.Intent
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.graphics.PathUtils
import com.bumptech.glide.Glide
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.peekster.Player_Video
import com.peekster.database.movie.database_utilss

import android.graphics.Bitmap

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.couchbase.lite.*
import com.peekster.Interfaces.MovieListiner
import com.peekster.Utils.Utills
import com.peekster.adapter.Movie_Category_Adapter
import kotlinx.android.synthetic.main.row_movies.*
import kotlinx.android.synthetic.main.row_movies.view.*


class Movie_Found (): Fragment() {
    
    val TAG="MOVIE_Grid"
    lateinit var poster_Img:ImageView
    lateinit var bitmap: Bitmap

    lateinit var category: String
    lateinit var db:Database;
    lateinit var MovieListiner:MovieListiner;

    val list:ArrayList<Result> = arrayListOf();
    lateinit var adapter:Movie_Category_Adapter;


    constructor(s:String,listiner:MovieListiner) : this() {
        category=s
        MovieListiner=listiner
        Log.e(TAG+".constructor","set category "+s)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e(TAG,"Showing screen ")


        var v:View=inflater.inflate(com.peekster.R.layout.row_movies, container, false)

        adapter=Movie_Category_Adapter(context!!).setList(list)
         var lm:LinearLayoutManager=LinearLayoutManager( context)
        lm.orientation=LinearLayoutManager.HORIZONTAL
        var row_list:RecyclerView=v.row_movies_reclerView
        row_list.layoutManager=lm
        row_list.adapter=adapter
        load_movies()
        return v
    }
    fun load_movies(){
        Log.e(TAG,"loading movies "+category)

        list.clear()
        db=database_utilss.getDatabase(context!!)
        android.os.Handler().post({
            val query = QueryBuilder.select(
                SelectResult.expression(Meta.id),
                SelectResult.property("title"),
                SelectResult.property("startchar"),
                SelectResult.property("category"),
                SelectResult.property("time"),
                SelectResult.property("poster_blob"),
                SelectResult.property("path")
            ).from(com.couchbase.lite.DataSource.database(db))
                .where(
                    Expression.property("type").equalTo(Expression.string("moive")) .and(
                        Expression.property("category").equalTo(Expression.string(category))
                    )
                )
            val result=query.execute();


            result.allResults().forEach({ result: Result? ->
                list.add(result!!)
                Log.e(TAG,category+"movie  "+result.getString("category"))

            })
            adapter.setList(list)
            adapter.notifyDataSetChanged()

            if(list.size==0 && MovieListiner!=null){
                MovieListiner.No_Movie_Found()
            }


//            notifyDataSetChanged()
            Log.e(TAG,category+"Loaded movies  "+list.size)



        })
    }


}