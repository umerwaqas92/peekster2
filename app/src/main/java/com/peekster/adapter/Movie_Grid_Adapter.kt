package com.peekster.adapter

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.couchbase.lite.*
import com.peekster.Interfaces.clicked_last_item
import com.peekster.MainActivity_2
import com.peekster.R
import com.peekster.database.movie.database_utilss

import kotlinx.android.synthetic.main.row_movies.view.*


class Movie_Grid_Adapter (val context: Context): RecyclerView.Adapter<Movie_Grid_Adapter.ViewHolder>() {


    lateinit var db:Database
    lateinit var listner:clicked_last_item;
     var Category:String="";
     var TAG="MOVIE_LOAD"

    var Row_List:ArrayList<Grid_Row> = arrayListOf();

    var list:ArrayList<Result> = arrayListOf();
    init {
        refreash_data()
    }

    class Grid_Row{
     lateinit   var s:String
        var list:ArrayList<Result> = arrayListOf();

        constructor(s: String, list: ArrayList<Result>) {
            this.s = s
            this.list = list
        }
    }

    fun refreash_data(){
        Row_List.clear()
      var a="abcdefghijklmnopqrstuvwxyz"
        for(c:Char in a){
            load_movies(Category,null,c+"")


        }
    }


    fun setListiner(l:clicked_last_item): Movie_Grid_Adapter {
        listner=l
        return this
    }

    fun setCategory(s:String): Movie_Grid_Adapter {
        Category=s
        Log.e(TAG,"Category set to  "+Category)

        return this
    }



    fun setList(s:ArrayList<Result>): Movie_Grid_Adapter {
        list=s
        return this
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_movies,parent,false))
    }

    override fun getItemCount(): Int {
        return Row_List.size
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {




        try {


            var adopter=Movie_Category_Adapter(context)
            adopter.list=Row_List.get(position).list
            var lm:LinearLayoutManager= LinearLayoutManager(context)
            lm.orientation=LinearLayoutManager.HORIZONTAL

            holder.recyler_view.adapter=adopter
            holder.recyler_view.layoutManager=lm



            holder.txt.setText(Row_List.get(position).s+"")

            adopter.notifyDataSetChanged()

        } catch (e: Exception) {
        }


    }




    fun load_movies(category:String, adapter: Movie_Category_Adapter?, char:String){
        var list:ArrayList<Result> = arrayListOf();

        db= database_utilss.getDatabase(context!!)
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
                    Expression.property("type").equalTo(Expression.string("moive"))
                        .and(Expression.property("category").equalTo(Expression.string(MainActivity_2.Selected_Caltegory)))
                            .and(Expression.property("startchar").equalTo(Expression.string(char.toUpperCase())))
                )
            val result=query.execute();


            result.allResults().forEach({ result: Result? ->
                list.add(result!!)
                Log.e(TAG,char.toString()+" "+result.getString("category")+" ,"+MainActivity_2.Selected_Caltegory+" movie  "+result.getString("startchar"))

            })
            if(list.size!=0){

                var gr = Grid_Row(char.toUpperCase(),list)
                Row_List.add(gr)

            }

//            Log.e(TAG,char+" loaded  movies grid_adatper"+list.size)
//            adapter.setList(list)

//            adapter.notifyDataSetChanged()

//            if(list.size==0 && MovieListiner!=null){
//                MovieListiner.No_Movie_Found()
//            }


            notifyDataSetChanged()
//            Log.e(TAG,category+"Loaded movies  "+list.size)



        })
    }




    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var recyler_view:RecyclerView=view.row_movies_reclerView
        var txt:TextView=view.row_movies_title
    }
}
