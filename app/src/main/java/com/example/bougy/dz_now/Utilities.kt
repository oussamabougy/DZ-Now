package com.example.bougy.dz_now

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import kotlin.random.Random

object Utilities {

    val monthHashMap = arrayOf("Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Aout","Septembre","Octobre","Novembre","Décembre")


    fun hasNetwork(context: Context): Boolean {
        var isConnected: Boolean = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork!!.isConnected)
            isConnected = true
        return isConnected
    }


    fun retrieveAuthResponse(context:Context):AuthResponse{
        val gson = Gson()
        val sharedPref = context.getSharedPreferences(
            "com.example.sayaradz_mobile.AUTH_RESPONSE", Context.MODE_PRIVATE)
        val authResponseString = sharedPref.getString("authResponse","")

        return  gson.fromJson(authResponseString,AuthResponse::class.java)
    }





    fun getPhotoUrl(): String{
        val photoUrls: ArrayList<String> = arrayListOf(
            "http://travelagent.ie/wp-content/uploads/2017/05/Car-Image.jpg",
            "https://sx-content-labs.sixt.io/thirdlight/seo/branches/content_ustates_190501_ford_mustang_shelby_20_35.jpg",
            "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AAElkil.img?h=426&w=624&m=6&q=60&u=t&o=t&l=f&x=619&y=450",
            "https://cdn.gearpatrol.com/wp-content/uploads/2018/03/15-of-the-Worst-Used-Cars-gear-patrol-lead-full.jpg",
            "http://img.timeinc.net/time/photoessays/2008/10_cars/kitt.jpg",
            "http://stat.overdrive.in/wp-content/uploads/2018/07/2019-Audi-Q3-SUV.jpg",
            "https://focus2move.com/wp-content/uploads/2019/03/Ford-Mustang_Shelby_GT500-2020-680x340.jpg",
            "https://www.classiccarsfriesland.com/wp-content/uploads/2018/12/ccf32.jpg",
            "https://cartrack.co.za/sites/default/files/car.jpg",
            "https://www.mercedes-benz.com/wp-content/uploads/sites/3/2018/02/mercedes-benz-passenger-cars-2018-a-class-w-177-amg-line-digital-white-pearl-2560x1440-2560x1440.jpg",
            "https://media.wired.com/photos/5a207b8740ed7f46060c7dda/master/w_2560%2Cc_limit/lucid-roundup-TA.jpg",
            "https://images.unsplash.com/photo-1532974297617-c0f05fe48bff?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1000&q=80",
            "https://static.posters.cz/image/1300/fotobehang/vintage-car-cuba-havana-416x254-cm-premium-non-woven-130gsm-i68737.jpg",
            "https://cdn.pixabay.com/photo/2019/07/07/14/03/fiat-4322521__340.jpg",
            "https://cmsimages-alt.kbb.com/content/dam/kbb-editorial/make/rolls-royce/cullinan/2019-rolls-royce-cullinan-side.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg",
            "https://images.pexels.com/photos/170811/pexels-photo-170811.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "https://www.sciencesetavenir.fr/assets/img/2018/09/06/cover-r4x3w1000-5b91612a81afc-section-hero-background.jpg",
            "https://www.teslarati.com/wp-content/uploads/2019/06/tesla-model-s-refresh-concept.jpg",
            "https://st.motortrend.com/uploads/sites/10/2015/11/2015-tesla-model-s-sedan-angular-front.png",
            "https://img.autoplus.fr/news/2019/04/24/1537947/1200%7C800%7C1af8c7e95f9df8de31cbc146.jpg",
            "https://cdn.motor1.com/images/mgl/OjmPo/s1/tesla-model-s-render.jpg"
        )

        return photoUrls.get(Random.nextInt(0,photoUrls.count()))
    }
}