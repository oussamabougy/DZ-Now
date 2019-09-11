package com.example.bougy.dz_now

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.activity_filtered_list.*

class BookmarkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        setTitle(R.string.action_favoris)

        //getBookmark()
    }

//    fun getBookmark() {
//        var prefs: Prefs? = null
//        prefs = Prefs(this)
//
//        var body = ""
//        if (prefs.contains("actualities"))
//            body = prefs.actualities
//        val gson = GsonBuilder().create()
//
//        val homeFeedAll = gson.fromJson(body, HomeFeed::class.java)
//
//        var actualities: List<Article> = homeFeedAll.actualities.filter { it.title != "" }
//
//        var homeFeed = HomeFeed(actualities as ArrayList<Article>)
//        runOnUiThread {
//            recyclerView_bookmark.adapter = MainAdapter(homeFeed, this)
//        }
//
//    }
}
