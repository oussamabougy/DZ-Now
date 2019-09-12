package com.example.bougy.dz_now

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable? = null
    private var categroies: ArrayList<Categorie> = ArrayList()
    private var newsPapers: ArrayList<NewsPaper> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setTitle(R.string.action_settings)

        compositeDisposable = CompositeDisposable()

        recyclerView_theme.layoutManager = LinearLayoutManager(this)
        recyclerView_sites.layoutManager = LinearLayoutManager(this)

        loadSettings()

    }

    fun loadSettings() {
        val restService = Retrofit.getRetrofit().create(RestService::class.java)
        compositeDisposable?.add(restService.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleCategoriesResponse))
        compositeDisposable?.add(restService.getNewsPapers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleNewsPapersResponse))


    }
    fun handleCategoriesResponse(categories: List<Categorie>){
        themesProgressBar.visibility = View.GONE
        this.categroies.addAll(categories)
        recyclerView_theme.adapter = ThemeSettingAdapter(this.categroies)


    }

    fun handleNewsPapersResponse(newsPapers: List<NewsPaper>){
        sitesProgressBar.visibility = View.GONE
        this.newsPapers.addAll(newsPapers)
        recyclerView_sites.adapter = NewsPapersAdapter(this.newsPapers)


    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()

    }
}
class ThemeList(var themes: List<Theme>)
class Theme(val id: Int, val title: String, val titleAR:String, var checked: Boolean = true)
