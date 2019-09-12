package com.example.bougy.dz_now

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.activity_filtered_list.*

class BookmarkActivity : AppCompatActivity() {

    private var savedArticles : ArrayList<Article> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        setTitle(R.string.action_favoris)


        loadData()
    }

    fun loadData(){
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = ArticleDB.getInstance(this@BookmarkActivity)
                val dao = db?.articleDAO()
                savedArticles.addAll(dao!!.getArticles())
                runOnUiThread {
                    recyclerView_bookmark.adapter = MainAdapter(savedArticles,this@BookmarkActivity)
                }


                return null
            }

            override fun onPostExecute(result: Void?) {
                Toast.makeText(applicationContext, "Vos Articles sauvegardés", Toast.LENGTH_LONG).show()

            }
        }.execute()
    }


}
