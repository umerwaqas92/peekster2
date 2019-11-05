package com.peekster.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.couchbase.lite.MutableDocument
import com.google.android.material.snackbar.Snackbar
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.model.MediaFile
import com.peekster.Interfaces.clicked_last_item
import com.peekster.Interfaces.refresh_category
import com.peekster.R
import com.peekster.Utils.Utills
import com.peekster.adapter.Category_adapter
import com.peekster.database.movie.database_utilss
import kotlinx.android.synthetic.main.add_new_category.*
import kotlinx.android.synthetic.main.add_new_category.view.*
import kotlinx.android.synthetic.main.dialouge_categories.*
import kotlinx.android.synthetic.main.menu.view.*
import kotlinx.android.synthetic.main.no_movie_found.view.*
import java.lang.Exception
import java.util.*

class Add_new_ctegories :Fragment() {

    val TAG="MOVIE"
    lateinit var category:String

     public var refreshCategory: refresh_category? =null;
   lateinit var d: Dialog
   lateinit var bitmap: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        var v:View=inflater.inflate(com.peekster.R.layout.add_new_category,container,false)
        v.done.setOnClickListener({

           try {
               if(!add_category_new_edt.text.toString().isEmpty()){
                   database_utilss.getDatabase(context!!).save(  MutableDocument().setString("type","category")
                       .setString("title",add_category_new_edt.text.toString())
                       .setBoolean("selected",false)
                       .setString("id",UUID.randomUUID().toString())
                       .setBoolean("forChild",false))
                   if(refreshCategory!=null){
                       refreshCategory!!.refresh()
                       Snackbar.make(v,"Added",Snackbar.LENGTH_SHORT).show()

                   }
               }

           }catch (e:Exception){
               Snackbar.make(v,"Try again"+e.toString(),Snackbar.LENGTH_SHORT).show()

           }

        })
        v.new_movie_btn.setOnClickListener({
           d = Dialog(context!!)
            d.setContentView(R.layout.dialouge_categories)
            var adopter=Category_adapter(context!!,false).setDialoge(true).setListiner(object : clicked_last_item {
                override fun clicked(id: String, islast: Boolean) {
                    super.clicked(id, islast)
                    d.dismiss()
                    category=id
                    startActivityForResult(Utills.Load_Videos_Activity(context!!),1)

                }
            })
            var lm=LinearLayoutManager(context!!)
                lm.orientation=LinearLayoutManager.VERTICAL

            d.ctegory_recyclerView.layoutManager=lm
            d.ctegory_recyclerView.adapter=adopter
            d.show()

        })
        return  v
    }



    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            val files:ArrayList<MediaFile> ?= data?.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
            val file=files?.get(0)


            bitmap= ThumbnailUtils.createVideoThumbnail(file?.path, MediaStore.Images.Thumbnails.MINI_KIND)
//
            Utills.inset_movie(context!!,category,files?.get(0)?.path?:"",files?.get(0)?.name!!,bitmap)

            Log.e(TAG,"movie loaded"+file?.path)


        }


    }



}