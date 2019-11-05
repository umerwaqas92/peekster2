package com.peekster.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.couchbase.lite.*
import com.peekster.Interfaces.clicked_last_item
import com.peekster.Interfaces.movie_item_click
import com.peekster.Player_Video
import com.peekster.R
import com.peekster.fragments.No_Movie_Found

import kotlinx.android.synthetic.main.movie_item.view.*
///moive item adapter for horizontal list


class Movie_Category_Adapter (val context: Context): RecyclerView.Adapter<Movie_Category_Adapter.ViewHolder>() {


    lateinit var db:Database
    lateinit var listner:clicked_last_item;
    lateinit var Category:String;
     var TAG="MOVIE_LOAD"

    companion object{
        lateinit var movie_item_click:movie_item_click;

    }

    var list:ArrayList<Result> = arrayListOf();

    fun setListiner(l:clicked_last_item): Movie_Category_Adapter {
        listner=l
        return this
    }



    fun setList(s:ArrayList<Result>): Movie_Category_Adapter {
        list=s
        return this
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        if(position>=list.size){

          holder.textView.setImageResource(com.peekster.R.drawable.demo_poster)


            return
        }else{

            android.os.Handler().post(Runnable {
                var taskBlob= list.get(position).getBlob("poster_blob")
                val bytes = taskBlob.getContent()

                    //ThumbnailUtils.createVideoThumbnail(list.get(position).getString("title"), MediaStore.Images.Thumbnails.MINI_KIND)

                Glide.with(context!!)
                    .load(bytes)
                    .centerCrop()
                    .into(holder.textView)
                holder.textView.setOnClickListener(View.OnClickListener {
                    val result=list.get(position);
                    if(movie_item_click!=null){
                        movie_item_click.clicked(result)
                    }





                })
            })


            }






        }






    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var textView:ImageView=view.movie_item
    }
}
