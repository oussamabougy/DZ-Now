package com.example.bougy.dz_now

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sayaradz_mobile.Adapters.VideoAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_video.*
import androidx.recyclerview.widget.LinearLayoutManager



class VideoActivity : AppCompatActivity() {

    private val vidoList: ArrayList<Video> = ArrayList()
    private lateinit var videoIds: List<String>
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var adapter:VideoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        compositeDisposable = CompositeDisposable()


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_video)
        recyclerView.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(mLayoutManager)

        loadData()



    }

    private fun loadData(){
        val restService = Retrofit.getRetrofit().create(RestService::class.java)
        compositeDisposable?.add(restService.getVideos()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError))

    }

    fun handleError(t:Throwable){
        Toast.makeText(this,"Error: ${t.message}",Toast.LENGTH_LONG).show()
    }

    fun handleResponse(videoList:List<Video>){
        progressBarVideo.visibility = View.GONE
        this.vidoList.addAll(videoList)
        videoIds = videoList.map { v -> v.video_url.split('/').get(4) }
        adapter = VideoAdapter(videoIds.toTypedArray(), lifecycle)
        val recyclerView_video = findViewById<RecyclerView>(R.id.recyclerView_video)
        recyclerView_video.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()


    }
}
