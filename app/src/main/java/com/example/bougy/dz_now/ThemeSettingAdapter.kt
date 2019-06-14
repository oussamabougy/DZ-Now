package com.example.bougy.dz_now

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.actuality_row.view.*
import kotlinx.android.synthetic.main.theme_setting_row.view.*
import java.util.*

class ThemeSettingAdapter(val themeList: ThemeList) : RecyclerView.Adapter<CustomThemeViewHolder>() {


    //Number of items
    override fun getItemCount(): Int {
        return themeList.themes.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomThemeViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.theme_setting_row, p0, false)
        return CustomThemeViewHolder(cellForRow)
    }
    override fun onBindViewHolder(holder: CustomThemeViewHolder, p1: Int) {
        val theme = themeList.themes.get(p1)
        val lang = Locale.getDefault().getLanguage()
        when(lang){
            "ar" -> holder?.view?.theme_setting_switch?.text = theme.titleAR
            else -> holder?.view?.theme_setting_switch?.text = theme.title
        }
        holder?.view?.theme_setting_switch?.isChecked = theme.checked
        holder?.view?.theme_setting_switch?.setOnCheckedChangeListener{ buttonView, isChecked ->
            var prefs: Prefs? = null
            prefs = Prefs(holder?.view?.context)
            if (prefs.contains("themes")) {
                val gson = GsonBuilder().create()
                val themeList = gson.fromJson(prefs.themes, ThemeList::class.java)
                //some changes
                var themes = themeList.themes
                themes.mapIndexed { index, theme1 ->
                    if(theme1.title == theme.title) {
                        if (isChecked)
                            theme1.checked = true
                        else
                            theme1.checked = false
                    }
                    else theme
                }
                var themeListUpdated = ThemeList(themes)
                val body = gson.toJson(themeListUpdated)
                prefs.themes = body
            }
            Toast.makeText(holder?.view?.context, theme.title, Toast.LENGTH_SHORT).show()
        }
        //val thumbnailImageView = holder?.view?.imageView_main
        //Picasso.with(holder?.view?.context).load(travel.main_image).into(thumbnailImageView)

    }
}

class CustomThemeViewHolder(val view : View): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
        }
    }
}