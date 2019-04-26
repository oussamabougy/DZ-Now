package com.example.bougy.dz_now

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.actuality_row.view.*

class MainAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {


    //Number of items
    override fun getItemCount(): Int {
        return homeFeed.actualities.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.actuality_row, p0, false)
        return CustomViewHolder(cellForRow)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, p1: Int) {
        val actuality = homeFeed.actualities.get(p1)
        holder?.view?.textView_main_title?.text = actuality.title
        holder?.view?.textView_time_main?.text = actuality.time
        holder?.view?.textView_theme_main?.text = actuality.theme
        //val thumbnailImageView = holder?.view?.imageView_main
        //Picasso.with(holder?.view?.context).load(travel.main_image).into(thumbnailImageView)

        holder?.actuality = actuality
    }
}

class CustomViewHolder(val view : View, var actuality: Actuality? =null): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            print(123)
            val intent = Intent(view.context, ActualityDetailActivity::class.java)
            intent.putExtra("actualityId", actuality?.id)
            intent.putExtra("actualityTitle", actuality?.title)
            intent.putExtra("actualityDescription", actuality?.description)
            intent.putExtra("actualityTime", actuality?.time)
            intent.putExtra("actualitySaved", actuality?.saved)
            view.context.startActivity(intent)
        }
    }
}