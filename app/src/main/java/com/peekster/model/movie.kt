package com.peekster.model

import android.graphics.Bitmap

class movie {
  lateinit  var title:String
    lateinit  var poster:Bitmap

    fun settitle(t:String){
        title=t
    }

    fun setImage(poster:Bitmap){
        this.poster=poster
    }


}