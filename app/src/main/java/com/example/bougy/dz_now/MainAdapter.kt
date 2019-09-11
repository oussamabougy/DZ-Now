package com.example.bougy.dz_now

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

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
    }
}

class CustomViewHolder(val view : View, var article: Article? =null ): RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.textView_main_title)
    val date = view.findViewById<TextView>(R.id.textView_time_main)
    val category = view.findViewById<TextView>(R.id.textView_theme_main)
    val image = view.findViewById<ImageView>(R.id.imageView_main)
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, ActualityDetailActivity::class.java)
            intent.putExtra("article", article)
            view.context.startActivity(intent)
        }
    }
}