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
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import com.couchbase.lite.MutableDocument
import com.peekster.Player_Video
import com.peekster.database.movie.database_utilss
import kotlinx.android.synthetic.main.movie_item.view.*
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import android.R.attr.data
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Handler
import android.os.HandlerThread
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.couchbase.lite.Blob
import com.peekster.R
import com.peekster.Utils.Utills
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI


class No_Movie_Found (): Fragment() {
    
    val TAG="MOVIE"
    lateinit var poster_Img:ImageView
    lateinit var bitmap: Bitmap

    lateinit var category: String



    constructor(s:String) : this() {
        category=s
        Log.e(TAG+".constructor","set category "+s)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        var v:View=inflater.inflate(com.peekster.R.layout.no_movie_found, container, false)
//            FilePicker.Builder(activity, OnSelectFileListener {file ->
//                Log.e(TAG,"Load"+file.absoluteFile)
//            })
//                .directory("/storage/sdcard/MyFiles")
//                .show()

        poster_Img=v.movie_item
        v.add_new_movie.setOnClickListener({
            val intent = Intent(context, FilePickerActivity::class.java)
            intent.putExtra(FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(false)
                .setShowFiles(false)
                .enableImageCapture(false)
//                .setMaxSelection(1)
                .setShowVideos(true)
                .setSingleChoiceMode(true)
                .setShowAudios(true)
                .setSkipZeroSizeFiles(true)
                .build())
            startActivityForResult(intent, 1)

        })


        return v
    }

     fun inset_movie(category:String,path:String,title:String,poster:Bitmap){

         val mutableDoc = MutableDocument()
             .setString("type","moive")
             .setString("title",title)
             .setString("startchar",title.toUpperCase()[0]+"")
             .setString("path",path)
             .setString("category",category)
             .setLong("time",System.currentTimeMillis())
             .setBlob("poster_blob", Blob("image/jpeg",Utills.getBytesFromBitmap(poster))  )
         database_utilss.getDatabase(context!!).save(mutableDoc)
         Log.e(TAG,"saved ")


     }



    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            val files:ArrayList<MediaFile> ?= data?.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
            val file=files?.get(0)


            bitmap=ThumbnailUtils.createVideoThumbnail(file?.path,MediaStore.Images.Thumbnails.MINI_KIND)

            inset_movie(category,files?.get(0)?.path?:"",files?.get(0)?.name!!,bitmap)

            Glide.with(context!!)
                .load(bitmap)
                .centerCrop()
                .into(poster_Img)

            Log.e(TAG,"movie loaded"+file?.path)


        }


    }


}