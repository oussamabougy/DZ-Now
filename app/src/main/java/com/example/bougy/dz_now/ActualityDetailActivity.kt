package com.example.bougy.dz_now

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_actuality_detail.*

class ActualityDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actuality_detail)

        setTitle("Actuality Detail")

        textView_title.text = intent.getStringExtra("actualityTitle")
        textView_description.text = intent.getStringExtra("actualityDescription")
    }
}
