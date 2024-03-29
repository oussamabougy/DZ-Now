package com.example.bougy.dz_now

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_actuality_detail.*
import kotlinx.android.synthetic.main.actuality_row.*
import kotlinx.android.synthetic.main.actuality_row.view.*

class ActualityDetailActivity : AppCompatActivity() {

    var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actuality_detail)

        setTitle(R.string.news)
        article = intent.getSerializableExtra("article") as Article
        Log.i("ARTICLE", article!!.category.toString())

        textView_title.text = article!!.title
        textView_description.text = article!!.body


        Glide.with(this).load(article!!.image_url).into(imageView)


    }

    fun onClickSaveButton(view: View){

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = ArticleDB.getInstance(this@ActualityDetailActivity)
                val dao = db?.articleDAO()

                dao?.add(article!!)


                return null
            }

            override fun onPostExecute(result: Void?) {
                Toast.makeText(applicationContext, "Article Suvegardé", Toast.LENGTH_LONG).show()
                finish()

            }
        }.execute()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.share, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        var title = intent.getStringExtra("actualityTitle")
        var description = intent.getStringExtra("actualityDescription")
        when (item.itemId) {
            R.id.action_share -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, title+": "+description)
                intent.type = "text/plain"
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
