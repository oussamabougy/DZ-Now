package com.example.bougy.dz_now

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.theme_setting_row.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setTitle("Paramètres")

        recyclerView_theme.layoutManager = LinearLayoutManager(this)

        fetchSettings()

    }

    fun fetchSettings() {
        var body = "{" +
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
        println(body)
        var prefs: Prefs? = null
        prefs = Prefs(this)
        if (prefs.contains("themes"))
            body = prefs.themes
        else
            prefs.themes = body

        val gson = GsonBuilder().create()

        val themeList = gson.fromJson(body, ThemeList::class.java)
        runOnUiThread {
            recyclerView_theme.adapter = ThemeSettingAdapter(themeList)
        }

    }
}
class ThemeList(var themes: List<Theme>)
class Theme(val id: Int, val title: String, var checked: Boolean = true)
