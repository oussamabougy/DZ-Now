package com.example.bougy.dz_now

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_actuality_detail.*
import kotlinx.android.synthetic.main.activity_filtered_list.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.collections.ArrayList

class FilteredListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_list)

        val categoryTitle = intent.getStringExtra("title")
        setTitle(categoryTitle)


       // fetchJson()
    }

//    fun fetchJson() {
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
//        val categoryTitle = intent.getStringExtra("title")
//        var actualities: List<Article>
//
//        val lang = Locale.getDefault().getLanguage()
//        when(lang) {
//            "ar" -> actualities = homeFeedAll.actualities.filter { it.category == categoryTitle }
//            else -> actualities = homeFeedAll.actualities.filter { it.category == categoryTitle }
//        }
//        var homeFeed = HomeFeed(actualities as ArrayList<Article>)
//        runOnUiThread {
//            recyclerView_filtered.adapter = MainAdapter(homeFeed, this)
//        }
//
//    }
}
