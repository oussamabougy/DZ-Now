package com.example.bougy.dz_now

import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var allArticles: ArrayList<Article> = ArrayList()
    private var articles:ArrayList<Article> = ArrayList()
    private var adapter: MainAdapter? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)
        compositeDisposable = CompositeDisposable()


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout as DrawerLayout?,
            toolbar as Toolbar?, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        (drawer_layout as DrawerLayout?)!!.addDrawerListener(toggle)
        toggle.syncState()

        val nav_view = findViewById<NavigationView>(R.id.nav_view)

        nav_view.setNavigationItemSelectedListener(this)

        //recyclerView_main.layoutManager = GridLayoutManager(this)

        loadData()

        initApp()
    }

    fun loadData(){
        val restService = Retrofit.getRetrofit().create(RestService::class.java)
        compositeDisposable?.add(restService.getArticles()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse))

    }

    fun handleResponse(articleList: List<Article>){
        progressBar.visibility = View.GONE
        this.allArticles.clear()
        this.articles.clear()
        this.articles.addAll(articleList)
        this.allArticles!!.addAll(articleList)
        adapter = MainAdapter(articles, this)
        val recyclerView_main = findViewById<RecyclerView>(R.id.recyclerView_main)
        recyclerView_main.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()

    }

    override fun onBackPressed() {
        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
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
            R.id.action_refresh -> {
                progressBar.visibility = View.VISIBLE
                loadData()
                return true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_favoris -> {
                val intent = Intent(this, BookmarkActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        if (item.title == "Tout"){
            articles = allArticles

        }else{
            articles = allArticles.filter {
                item.title == it.category

            } as  ArrayList<Article>

        }


        adapter = MainAdapter(articles, this)
        val recyclerView_main = findViewById<RecyclerView>(R.id.recyclerView_main)
        recyclerView_main.adapter = adapter


        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun initApp() {
        renderDrawer()
        adapter  = MainAdapter(articles, this)

        runOnUiThread {
            val recyclerView_main = findViewById<RecyclerView>(R.id.recyclerView_main)
            recyclerView_main.adapter = adapter!!
        }
    }
    fun renderDrawer(){
        val restService = Retrofit.getRetrofit().create(RestService::class.java)
        compositeDisposable?.add(restService.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleCategoriesResponse))

    }
    fun handleCategoriesResponse(categories: List<Categorie>){
        val list = categories as ArrayList
        list.add(Categorie(99,"Tout"))
        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        list.mapIndexed { index, theme ->
            val group = nav_view.menu.getItem(0).subMenu
            val item = group.add(theme.category)
            item.setIcon(R.drawable.ic_feed)

        }


    }


}

