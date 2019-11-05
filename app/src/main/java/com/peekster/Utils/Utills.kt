package com.peekster.Utils

import android.content.Context
import android.content.Intent
import com.google.android.exoplayer2.util.Util.toByteArray
import android.graphics.Bitmap
import android.util.Log
import com.couchbase.lite.Blob
import com.couchbase.lite.MutableDocument
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.peekster.database.movie.database_utilss
import java.io.ByteArrayOutputStream


class Utills{
    companion object{
        var tag="MOVIE";

        fun getBytesFromBitmap(bitmap: Bitmap?): ByteArray? {
            if (bitmap != null) {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
                return stream.toByteArray()
            }
            return null
        }

        fun Load_Videos_Activity(context:Context):Intent{
            val intent = Intent(context, FilePickerActivity::class.java)
            intent.putExtra(
                FilePickerActivity.CONFIGS, Configurations.Builder()
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
            return intent
        }

        fun inset_movie(context: Context,category:String,path:String,title:String,poster:Bitmap){

            val mutableDoc = MutableDocument()
                .setString("type","moive")
                .setString("title",title)
                .setString("startchar",title.toUpperCase()[0]+"")
                .setString("path",path)
                .setString("category",category)
                .setLong("time",System.currentTimeMillis())
                .setBlob("poster_blob", Blob("image/jpeg", getBytesFromBitmap(poster))  )
            database_utilss.getDatabase(context!!).save(mutableDoc)
            Log.e("UTILLS","saved ")


        }
    }


}