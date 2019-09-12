package com.example.bougy.dz_now

import android.os.AsyncTask

import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_bookmark.*


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
                    val recyclerView_bookmark = findViewById<RecyclerView>(R.id.recyclerView_bookmark)
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
