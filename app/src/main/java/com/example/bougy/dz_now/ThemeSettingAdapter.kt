package com.example.bougy.dz_now

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.theme_setting_row.view.*
import kotlin.collections.ArrayList

class ThemeSettingAdapter(val context:Context,val categories: ArrayList<Categorie>, val favoriteCategories: ArrayList<Categorie>, val authToken: String) : RecyclerView.Adapter<CustomThemeViewHolder>() {


    //Number of items
    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomThemeViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.theme_setting_row, p0, false)
        return CustomThemeViewHolder(cellForRow)
    }
    override fun onBindViewHolder(holder: CustomThemeViewHolder, p1: Int) {
        val theme = categories.get(p1)
        holder?.view?.theme_setting_switch?.text = theme!!.name
        holder?.view?.theme_setting_switch?.isChecked = favoriteCategories.map { f -> f.name }.contains(theme.name)
        holder?.view?.theme_setting_switch?.setOnCheckedChangeListener{ buttonView, isChecked ->
            val restService = Retrofit.getRetrofit().create(RestService::class.java)
            val compositeDisposable = CompositeDisposable()
            if(isChecked){
                compositeDisposable?.add(restService.addFavoriteCategory(authToken,categories.get(p1).id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleAddCategoryResponse))
            }else{
                compositeDisposable?.add(restService.deleteFavoriteCategory(authToken,categories.get(p1).id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleDeleteCategoryResponse))

            }



//            Toast.makeText(holder?.view?.context, theme.name, Toast.LENGTH_SHORT).show()
        }


    }
    fun handleDeleteCategoryResponse(){
        Toast.makeText(context, "La catégorie a été supprimé de vos favoris" , Toast.LENGTH_SHORT).show()



    }
    fun handleAddCategoryResponse(categorie: Categorie){
        Toast.makeText(context, "La catégorie a été ajouté à vos favoris" , Toast.LENGTH_SHORT).show()

    }

}

class CustomThemeViewHolder(val view : View): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
        }
    }
}

class SourceAdapter(val context: Context,val sourceList: ArrayList<Source>, val favoriteSources: ArrayList<Source>, val authToken: String ) : RecyclerView.Adapter<CustomThemeViewHolder>() {


    //Number of items
    override fun getItemCount(): Int {
        return sourceList.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomThemeViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.theme_setting_row, p0, false)
        return CustomThemeViewHolder(cellForRow)
    }
    override fun onBindViewHolder(holder: CustomThemeViewHolder, p1: Int) {
        val newsPaper = sourceList.get(p1)
        holder?.view?.theme_setting_switch?.text = newsPaper.name
        holder?.view?.theme_setting_switch?.isChecked = favoriteSources.contains(newsPaper)
        holder?.view?.theme_setting_switch?.setOnCheckedChangeListener{ buttonView, isChecked ->

            val restService = Retrofit.getRetrofit().create(RestService::class.java)
            val compositeDisposable = CompositeDisposable()
            if(isChecked){
                compositeDisposable?.add(restService.addFavoriteSite(authToken,sourceList.get(p1).id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleAddSiteResponse))
            }else{
                compositeDisposable?.add(restService.deleteFavoriteSite(authToken,sourceList.get(p1).id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleDeleteSiteResponse))

            }
        }

    }

    fun handleDeleteSiteResponse(){
        Toast.makeText(context, "Le site a été supprimé de vos favoris" , Toast.LENGTH_SHORT).show()



    }
    fun handleAddSiteResponse(source: Source){
        Toast.makeText(context, "Le site a été ajouté à vos favoris" , Toast.LENGTH_SHORT).show()

    }
}
