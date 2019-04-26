package com.example.bougy.dz_now

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_actuality_detail.*

class ActualityDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actuality_detail)

        setTitle("Actuality Detail")

        textView_title.text = intent.getStringExtra("actualityTitle")
        textView_description.text = intent.getStringExtra("actualityDescription")
        saved_switch.isChecked = intent.getBooleanExtra("actualitySaved",false)
        saved_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            var id = intent.getIntExtra("actualityId", 0)
            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
            var prefs: Prefs? = null
            prefs = Prefs(this)

            var body = ""
            if (prefs.contains("actualities"))
                body = prefs.actualities
            val gson = GsonBuilder().create()

            val actualityList = gson.fromJson(body, HomeFeed::class.java)

            var actualities = actualityList.actualities

            actualities.mapIndexed { index, actuality ->
                if(actuality.id == id) {
                    if (isChecked)
                        actuality.saved = true
                    else
                        actuality.saved = false
                }
                else actuality
            }

            var actualityListUpdated = HomeFeed(actualities)
            body = gson.toJson(actualityListUpdated)
            prefs.actualities = body


        }
    }
}
