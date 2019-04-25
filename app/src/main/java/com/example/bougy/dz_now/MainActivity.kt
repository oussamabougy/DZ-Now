package com.example.bougy.dz_now

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        recyclerView_main.layoutManager = LinearLayoutManager(this)

        initApp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val intent = Intent(this, FilteredListActivity::class.java)
        intent.putExtra("title", item.title)
        startActivity(intent)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun initApp() {
        var prefs: Prefs? = null
        prefs = Prefs(this)
        if (!prefs.contains("themes")) {
            var themesData = "{" +
                    "'themes':[" +
                    "{" +
                    "'id':1," +
                    "'title':'Politique'," +
                    "'checked': true"+
                    "}," +
                    "{" +
                    "'id':2," +
                    "'title':'Sport'," +
                    "'checked': true"+
                    "}," +
                    "{" +
                    "'id':3," +
                    "'title':'Culture'," +
                    "'checked': false"+
                    "}" +
                    "]" +
                    "}"
            prefs.themes = themesData
        }
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
                "'title':'Hirak Algeria'," +
                "'description': 'BREXIT - Un compromis qui ne manque pas d ironie. Dans la soirée du mercredi 10 au jeudi 11 avril, l Union européenne a proposé au Royaume-Uni de reporter la date du Brexit au 31 octobre, après un point d étape en juin, ont rapporté plusieurs sources diplomatiques. '," +
                "'main_image': 'https://9c998969b63acdb676d1-37595348221e1b716e1a6cfee3ed7891.ssl.cf1.rackcdn.com/almpics/2014/02/RTR22VZQ.jpg/RTR22VZQ-870.jpg'," +
                "'time': '2h',"+
                "'theme': 'Politique'"+
                "}," +
                "{" +
                "'id':3," +
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

        val homeFeedAll = gson.fromJson(body, HomeFeed::class.java)


        var themeData = ""
        var prefs: Prefs? = null
        prefs = Prefs(this)
        if (prefs.contains("themes"))
            themeData = prefs.themes

        val themeList = gson.fromJson(themeData, ThemeList::class.java)

        var actualities: List<Actuality> = homeFeedAll.actualities.filter {
            var theme = it.theme
            themeList.themes.filter { it.title == theme }.single().checked
        }

        var homeFeed = HomeFeed(actualities)

        runOnUiThread {
            recyclerView_main.adapter = MainAdapter(homeFeed)
        }

    }
}

class HomeFeed(val actualities: List<Actuality>)

class Actuality(val id: Int, val title: String, val description: String, val main_image: String, val time: String, val theme: String)

class Prefs (context: Context) {
    val PREFS_FILENAME = "com.prefs"
    val THEMES = "themes"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    fun contains(keywork:String):Boolean{
        return prefs.contains(keywork)
    }
    var themes: String
        get() = prefs.getString(THEMES, "")
        set(value) = prefs.edit().putString(THEMES, value).apply()
}