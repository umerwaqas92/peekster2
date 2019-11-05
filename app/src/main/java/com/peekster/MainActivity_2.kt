package com.peekster

import android.content.Context
import android.os.Bundle


import com.peekster.Utils.MyActivity
import android.provider.MediaStore

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.peekster.database.movie.database_utilss
import java.util.*
import kotlin.collections.ArrayList
import android.R.string
import com.couchbase.lite.DataSource.database
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.opengl.Visibility
import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import com.couchbase.lite.*
import com.google.android.material.snackbar.Snackbar
import com.peekster.Interfaces.MovieListiner
import com.peekster.Interfaces.clicked_last_item
import com.peekster.Interfaces.movie_item_click
import com.peekster.Interfaces.refresh_category
import com.peekster.Utils.Utills
import com.peekster.adapter.Category_adapter
import com.peekster.adapter.Movie_Category_Adapter
import com.peekster.fragments.*
import kotlinx.android.synthetic.main.activity_main_2.*
import kotlinx.android.synthetic.main.add_new_category.*


class MainActivity_2 : MyActivity() {

    val TAG="FILES_LOADING"

    var isShowing_details=false

   lateinit var ad:Category_adapter;
   lateinit var db:Database;

    companion object{
        var Selected_Caltegory:String="";
        var Selected_Caltegory_id:String="";

    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_2)

        imageView3.setOnClickListener({
            if(isShowing_details){
            hide_movie_details()
                imageView3.setImageResource(R.drawable.menu_icon)
            }
        })

//        (switch_kids as Switch).setChange


        var fm:FragmentManager = supportFragmentManager
        var trns:FragmentTransaction  =fm.beginTransaction()
        trns.replace(R.id.feagment_container,No_Movie_Found(""))
        trns.commit()
        db=database_utilss.getDatabase(this)



        val query = QueryBuilder.select(SelectResult.all())
            .from(DataSource.database(db))
            .where(Expression.property("type").equalTo(Expression.string("category")))
        val result = query.execute()


        Movie_Category_Adapter.movie_item_click=object : movie_item_click {
            override fun clicked(r:Result) {
                var fm:FragmentManager = supportFragmentManager
                var trns:FragmentTransaction  =fm.beginTransaction()
                fragment_movie_details.movie_details=r
                trns.replace(R.id.feagment_container,fragment_movie_details())
                trns.commit()

                linearLayout.visibility=View.GONE
                isShowing_details=true
                imageView3.setImageResource(R.drawable.ic_arrow_back)


            }
        }


        ad=Category_adapter(this,false)

        ad.setListiner(object : clicked_last_item {
            override fun clicked(id:String,b:Boolean) {
                super.clicked(id,b)
//                linearLayout.visibility=View.VISIBLE

                if(b){
                    var Fragment_AddNewCatigories=Add_new_ctegories()
                    Fragment_AddNewCatigories.refreshCategory=object : refresh_category {
                        override fun refresh() {
//                        Snackbar.make(add_category_new_edt,"Added", Snackbar.LENGTH_SHORT).show()
                            Log.e("MOVIE_category","clicked "+id)

                            ad.refresh_data()
                            Log.e("MOVIE","refreashed from fragment "+id)


//                    ad.notifyDataSetChanged()
                        }
                    }
                    var fm:FragmentManager = supportFragmentManager
                    var trns:FragmentTransaction  =fm.beginTransaction()
                    trns.replace(R.id.feagment_container,Fragment_AddNewCatigories)
                    trns.commit()

                }else{


                    Selected_Caltegory_id=id
                    show_movie_category(id)


                }




            }
        })


        ctegory_recyclerView.adapter=ad
        var lm:LinearLayoutManager =LinearLayoutManager(this)
        lm.orientation=LinearLayoutManager.VERTICAL

        ctegory_recyclerView.layoutManager=lm

//        Log.e(Utills.tag,"categories loaded "+result.allResults().)
        val size=result.allResults().size



        if(size==0){


            db.save(  MutableDocument().setString("type","category")
                .setString("title","Adventure")
                .setBoolean("selected",false)
                .setString("id",UUID.randomUUID().toString())

                .setBoolean("forChild",false))

            db.save(  MutableDocument().setString("type","category")
                .setString("title","Action")
                .setBoolean("selected",false)
                .setString("id",UUID.randomUUID().toString())

                .setBoolean("forChild",false))

            db.save(  MutableDocument().setString("type","category")
                .setString("title","Horrror")
                .setBoolean("selected",false)
                .setString("id",UUID.randomUUID().toString())

                .setBoolean("forChild",false))




            Log.e(Utills.tag,"categories saved "+result.allResults().size)

        }else{
            Log.e(Utills.tag,"categories ")
        }
        




    }

    fun show_movie_category(id:String){
        MainActivity_2.Selected_Caltegory=id
        Log.e("MOVIE_category","clicked "+id)

        var NoMovie:Fragment=Movies_Grid(id,object : MovieListiner {///Movie_Found

            override fun No_Movie_Found() {
                super.No_Movie_Found()
                var fm:FragmentManager = supportFragmentManager
                var trns:FragmentTransaction  =fm.beginTransaction()
                trns.replace(R.id.feagment_container,No_Movie_Found(id))
                trns.commit()

            }

        }
        )



        var fm:FragmentManager = supportFragmentManager
        var trns:FragmentTransaction  =fm.beginTransaction()
        trns.replace(R.id.feagment_container,NoMovie)
        trns.commit()
    }

    fun hide_movie_details(){
        isShowing_details=false
        show_movie_category(MainActivity_2.Selected_Caltegory_id)
//        var fm:FragmentManager = supportFragmentManager
//        var trns:FragmentTransaction  =fm.beginTransaction()
//        trns.replace(R.id.feagment_container,No_Movie_Found())
//        trns.commit()
        linearLayout.visibility=View.VISIBLE
    }
    override fun onBackPressed() {
        if(isShowing_details){
            hide_movie_details()
        }else{
            super.onBackPressed()
        }

    }





}
