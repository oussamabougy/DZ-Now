package com.example.bougy.dz_now

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_bookmark.*

class MainAdapter(val articleList: ArrayList<Article>, val context:Context) : RecyclerView.Adapter<CustomViewHolder>() {


    //Number of items
    override fun getItemCount(): Int {
        return articleList.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.actuality_row, p0, false)
        return CustomViewHolder(cellForRow)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, p1: Int) {
        val article = articleList.get(p1)
        holder.title.text = article.title
        holder.category.text = article.category
        holder.date.text =  "Date:" + article.date
        Glide.with(context).load(articleList.get(p1).image_url).into(holder.image)



        holder.article = article
        holder.checkIfSaved()
    }
}

class CustomViewHolder(val view : View ): RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.textView_main_title)
    val date = view.findViewById<TextView>(R.id.textView_time_main)
    val category = view.findViewById<TextView>(R.id.textView_theme_main)
    val image = view.findViewById<ImageView>(R.id.imageView_main)
    var saved = false
    var article:Article? = null

    fun checkIfSaved(){
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = ArticleDB.getInstance(view.context)
                val dao = db?.articleDAO()

                Log.i("article",article!!.id.toString())

                if(dao!!.getArticle(article!!.id) != null){
                    saved = true
                }
                return null
            }
        }.execute()

    }
    init {

        view.setOnClickListener {
            var intent: Intent
            if (saved){

                intent = Intent(view.context, BookmarkedActualityDetailActivity::class.java)

            }else{
                intent = Intent(view.context, ActualityDetailActivity::class.java)
            }

            intent.putExtra("article", article)
            view.context.startActivity(intent)

            
            
        }
    }
}