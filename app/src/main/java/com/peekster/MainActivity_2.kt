package com.peekster

import android.content.Context
import android.os.Bundle


import com.peekster.Utils.MyActivity
import android.provider.MediaStore
import java.nio.file.Files.size
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log


class MainActivity_2 : MyActivity() {

    val TAG="FILES_LOADING"

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_2)
        getAllVideoPath(this)






    }


    private fun getAllVideoPath(context: Context): Array<String> {
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Video.VideoColumns.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        val pathArrList = ArrayList<String>()
        //int vidsCount = 0;
        if (cursor != null) {
            //vidsCount = cursor.getCount();
            //Log.d(TAG, "Total count of videos: " + vidsCount);
            while (cursor.moveToNext()) {
                pathArrList.add(cursor.getString(0))
                Log.e(TAG, cursor.getString(0));
            }
            cursor.close()
        }

        return pathArrList.toArray(arrayOfNulls(pathArrList.size))
    }


}
