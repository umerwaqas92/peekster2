package com.peekster.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.couchbase.lite.*
import com.peekster.Player_Video
import com.peekster.adapter.Movie_Category_Adapter
import com.peekster.database.movie.database_utilss
import kotlinx.android.synthetic.main.movie_details.*
import kotlinx.android.synthetic.main.movie_details.view.*
import kotlinx.android.synthetic.main.row_movies.view.*

class fragment_movie_details ():Fragment(){

    val TAG="fragment_movie_details"

    companion object{
      lateinit  var movie_details:Result;
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e(TAG,"Showing screen ")


        var v: View =inflater.inflate(com.peekster.R.layout.movie_details, container, false)

        v.textView5.setText(movie_details.getString("title"))

        var taskBlob= movie_details.getBlob("poster_blob")
        val bytes = taskBlob.getContent()


//movie_details.getString("category")
        var  db= database_utilss.getDatabase(context!!)
        val query = QueryBuilder.select(
            SelectResult.expression(Meta.id),
            SelectResult.property("title"),
            SelectResult.property("type"),
            SelectResult.property("forChild")

        )
            .from(DataSource.database(db))
            .where(
                Expression.property("type").equalTo(Expression.string("category"))
                    .and(
                    Meta.id.equalTo(Expression.string(movie_details.getString("category")))
                )
            )

        val result = query.execute()


        v.textView8.setText(result.allResults().get(0).getString("title"))

        Glide.with(context!!)
            .load(bytes)
            .into(v.imageView6)

        Glide.with(context!!)
            .load(bytes)
            .into(v.image_background)


        v.button.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context,Player_Video::class.java).putExtra("path", movie_details.getString("path")))

        })

        return v
    }
}