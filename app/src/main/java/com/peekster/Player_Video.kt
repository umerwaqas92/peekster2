package com.peekster

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.peekster.Utils.MyActivity

import kotlinx.android.synthetic.main.activity_player__video.*
import kotlinx.android.synthetic.main.content_player__video.*
import kotlinx.android.synthetic.main.player_controler.*
import java.io.File
import java.net.URI

class Player_Video : MyActivity() {


    internal var player: ExoPlayer? = null
    lateinit var context:Context;

    var  TAG="PLAYER_VIDEO"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player__video)
        context=this;
        var uri:Uri = Uri.fromFile(File(intent.getStringExtra("path")!!))

        Log.e(TAG,intent.getStringExtra("path")!!)
//        if(uri!=null){
            initializePlayer(uri)
//        }

    }


    fun initializePlayer(uri: Uri?) {
        if (uri == null) {
            return
        }

        //        Uri uri = Uri.parse("http://dl86.y2mate.com/?file=M3R4SUNiN3JsOHJ6WWQ2a3NQS1Y5ZGlxVlZIOCtyZ21rTlFveUFNaVBydFZwNThRdytHeUl2WkpPNnNEOG9XeEJlRkg5RGJSWSsrS1BBYTJ0SmtoUlhLKy9kbzBreTdxKzZrSFV1Zy9Xd2FyM2VDcW1qUkptUWJoZG9XSVI1NVBZbUp2dGtKbWxuUzF3ZUdIL0VmZTZ6ai9nVlRSWVNVZXQzVUlNdmlidk5SSzNXalpZT1RsN0lBY3JEU2c5cDliMjgrYm9RMzYvTDB0ck9ObVgweDlZYTljMVpQSzNmSExva1ltaXBVVjhGeUJvY1dCRGI5N0ViWE5QelI4TnljSC9jYmZBVWxPbld0SW9TTHZvK0Z5dnlZTWFiQjgrbWVnOFAvc08yN05LSmV2RzhUUWViaz0%3D");
        //        Uri uri = Uri.parse("http://dl54.y2mate.com/?file=M3R4SUNiN3JsOHJ6WWQ2a3NQS1Y5ZGlxVlZIOCtyZ21rTlFveUFNaVBydFZwNThRdytHeUl2WkpPNnNEOG9XeEJlRkg5RGJSWSsrS1BBYTJ0SmtoUlhLKy9kbzBreTdxKzZrSFV1aE9CMGFneS9idG15RWl6d0g4Y3QzeUJibENaU28rOFdaNjNET0gyZlBSL0VmZXZtMzU0aCtsTzJGWWxDSlpNUFRFOU5GTDJtU0FJcnptMjVFWG9qV2UzWXBRMitLaW9BVGt3bzA0cmRCNVlVdG5aNjlPMkpMOHpjL05xVTRtZ3BnYjNVeWJvKzZ2TEk4UUZJSzVjaFpQYjNKUnVmK2hVUXRLaVNZUzhXbXc0b1lWc3pKWkkrNGpzU3psckx5K04zbWRadEwvWFpTUUkrVzQrNVNzdEt3NDZ4U1g5ckU9");
        //        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);


        val mediaSource = ExtractorMediaSource.Factory(DefaultDataSourceFactory(context, "ua"))
            .createMediaSource(uri)




        player = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        )


        video_view.setControllerVisibilityListener {

//            Snackbar.make(video_view,"changes",Snackbar.LENGTH_SHORT).show()

            if(player_back_btn.visibility==View.GONE){
            player_back_btn.visibility=View.VISIBLE
        }else{
            player_back_btn.visibility=View.GONE
        }

        }
        //        MediaSource mediaSource=buildMediaSource(audioSource);
        video_view.setPlayer(player)

        player?.setPlayWhenReady(true)
        player?.seekTo(0, 0)
        player?.prepare(mediaSource, true, false)

    }
}
