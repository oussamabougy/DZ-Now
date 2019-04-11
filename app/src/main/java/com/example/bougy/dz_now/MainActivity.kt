package com.example.bougy.dz_now

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        recyclerView_main.layoutManager = LinearLayoutManager(this)

        fetchJson()
    }

    fun fetchJson() {
        val body = "{" +
                "'actualities':[" +
                "{" +
                "'id':1," +
                "'title':'Royaume-Uni reporte le Brexit au 31 octobre'," +
                "'description': 'BREXIT - Un compromis qui ne manque pas d ironie. Dans la soirée du mercredi 10 au jeudi 11 avril, l Union européenne a proposé au Royaume-Uni de reporter la date du Brexit au 31 octobre, après un point d étape en juin, ont rapporté plusieurs sources diplomatiques. '," +
                "'main_image': 'https://9c998969b63acdb676d1-37595348221e1b716e1a6cfee3ed7891.ssl.cf1.rackcdn.com/almpics/2014/02/RTR22VZQ.jpg/RTR22VZQ-870.jpg'," +
                "'time': '2h',"+
                "'theme': 'Politique'"+
                "}," +
                "{" +
                "'id':2," +
                "'title':'AJAX AMSTERDAM 1-1 JUVENTUS TURIN'," +
                "'description': 'LIGUE DES CHAMPIONS – On s attendait à un match intense entre deux historiques du football européen, ils nous ont servi. Si personne ne repart avec la victoire, tous les supporters peuvent être heureux de ce qu ils ont vu (1-1).'," +
                "'main_image': 'https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf'," +
                "'time': '3h',"+
                "'theme': 'Sport'"+
                "}" +
                "]" +
                "}"
        println(body)
        val gson = GsonBuilder().create()

        val homeFeed = gson.fromJson(body, HomeFeed::class.java)
        runOnUiThread {
            recyclerView_main.adapter = MainAdapter(homeFeed)
        }

    }
}

class HomeFeed(val actualities: List<Actuality>)

class Actuality(val id: Int, val title: String, val description: String, val main_image: String, val time: String, val theme: String)