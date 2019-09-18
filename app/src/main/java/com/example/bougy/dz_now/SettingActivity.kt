package com.example.bougy.dz_now

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable? = null
    private var favoriteCategories: ArrayList<Categorie> = ArrayList()
    private var allCategories: ArrayList<Categorie> = ArrayList()
    private var favoriteSources: ArrayList<Source> = ArrayList()
    private var allSources: ArrayList<Source> = ArrayList()
    private lateinit var authResponse:AuthResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setTitle(R.string.action_settings)

        compositeDisposable = CompositeDisposable()

        val recyclerView_theme = findViewById<RecyclerView>(R.id.recyclerView_theme)
        val recyclerView_sites = findViewById<RecyclerView>(R.id.recyclerView_sites)


        recyclerView_theme.layoutManager = LinearLayoutManager(this)
        recyclerView_sites.layoutManager = LinearLayoutManager(this)

        loadSettings()

    }

    fun loadSettings() {
        val restService = Retrofit.getRetrofit().create(RestService::class.java)
        authResponse = Utilities.retrieveAuthResponse(this)
        if (authResponse != null){
            compositeDisposable?.add(restService.getFavoriteCategories("Token "+authResponse.auth_token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCategoriesResponse))
            compositeDisposable?.add(restService.getFavoriteSites("Token "+authResponse.auth_token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleNewsPapersResponse))




        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }




    }
    fun handleCategoriesResponse(categories: List<FavoriteCategory>){
        this.favoriteCategories.addAll(categories.map { f -> f.category })
        val restService = Retrofit.getRetrofit().create(RestService::class.java)
        compositeDisposable?.add(restService.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleAllCategoriesResponse))
    }

    fun handleAllCategoriesResponse(categories: List<Categorie>){
        themesProgressBar.visibility = View.GONE
        this.allCategories.addAll(categories)
        val recyclerView_theme = findViewById<RecyclerView>(R.id.recyclerView_theme)
        recyclerView_theme.adapter = ThemeSettingAdapter(this,this.allCategories,this.favoriteCategories,"Token "+authResponse.auth_token )


    }

    fun handleNewsPapersResponse(newsPapers: List<Source>){

        this.favoriteSources.addAll(newsPapers)
        val restService = Retrofit.getRetrofit().create(RestService::class.java)
        compositeDisposable?.add(restService.getSources()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleAllSourcesResponse))



    }

    fun handleAllSourcesResponse(newsPapers: List<Source>){
        sitesProgressBar.visibility = View.GONE
        this.allSources.addAll(newsPapers)
        val recyclerView_sites = findViewById<RecyclerView>(R.id.recyclerView_sites)
        recyclerView_sites.adapter = SourceAdapter(this, this.allSources, this.favoriteSources, "Token "+authResponse.auth_token)


    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()

    }
}
class ThemeList(var themes: List<Theme>)
class Theme(val id: Int, val title: String, val titleAR:String, var checked: Boolean = true)
