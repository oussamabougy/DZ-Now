package com.example.bougy.dz_now

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.actuality_row.view.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var actualities: ArrayList<Article> = ArrayList()
    private var adapter: MainAdapter? = null
    private var themeList: ThemeList? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        compositeDisposable = CompositeDisposable()


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //recyclerView_main.layoutManager = GridLayoutManager(this)

        loadData()

        initApp()
    }

    fun loadData(){
        val restService = Retrofit.getRetrofit().create(RestService::class.java)
        compositeDisposable?.add(restService.getArticles()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse))

    }

    fun handleResponse(articleList: List<Article>){
        this.actualities!!.addAll(articleList)
        adapter!!.notifyDataSetChanged()

    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_favoris -> {
                val intent = Intent(this, BookmarkActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        actualities = actualities.filter {
            item.title == it.category

        } as ArrayList<Article>

        adapter = MainAdapter(actualities, this)
        recyclerView_main.adapter = adapter



        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun initApp() {
        var prefs: Prefs? = null
        prefs = Prefs(this)
        if (!prefs.contains("themes")) {
            var themesData = "{" +
                    "'themes':[" +
                    "{" +
                    "'id':1," +
                    "'title':'Politique'," +
                    "'titleAR':'سياسة'," +
                    "'checked': true"+
                    "}," +
                    "{" +
                    "'id':2," +
                    "'title':'Sport'," +
                    "'titleAR':'رياضة'," +
                    "'checked': true"+
                    "}," +
                    "{" +
                    "'id':3," +
                    "'title':'Culture'," +
                    "'titleAR':'ثقافة'," +
                    "'checked': true"+
                    "}," +
                    "{" +
                    "'id':4," +
                    "'title':'Jeux'," +
                    "'titleAR':'ألعاب'," +
                    "'checked': true"+
                    "}," +
                    "{" +
                    "'id':5," +
                    "'title':'Economie'," +
                    "'titleAR':'إقتصاد'," +
                    "'checked': true"+
                    "}," +
                    "{" +
                    "'id':5," +
                    "'title':'Education'," +
                    "'titleAR':'تعليم'," +
                    "'checked': true"+
                    "}" +
                    "]" +
                    "}"
            prefs.themes = themesData
        }
        if(!prefs.contains("actualities")) {
            val actualitiesData = "{" +
                    "'actualities':[" +
                    "{" +
                    "'id':1," +
                    "'title':'Royaume-Uni reporte le Brexit au 31 octobre'," +
                    "'titleAR':'بريطانيا تؤجل الإنتخابات'," +
                    "'description': 'BREXIT - Un compromis qui ne manque pas d ironie. Dans la soirée du mercredi 10 au jeudi 11 avril, l Union européenne a proposé au Royaume-Uni de reporter la date du Brexit au 31 octobre, après un point d étape en juin, ont rapporté plusieurs sources diplomatiques.'," +
                    "'descriptionAR': 'BREXIT - حل وسط لا يفتقر إلى المفارقة. في مساء يوم الأربعاء 10 إلى الخميس 11 أبريل ، اقترح الاتحاد الأوروبي على المملكة المتحدة تأجيل موعد خروج بريطانيا من الاتحاد الأوروبي إلى 31 أكتوبر ، بعد معلم بارز في يونيو ، حسبما ذكرت عدة مصادر دبلوماسية'," +
                    "'main_image': 'https://9c998969b63acdb676d1-37595348221e1b716e1a6cfee3ed7891.ssl.cf1.rackcdn.com/almpics/2014/02/RTR22VZQ.jpg/RTR22VZQ-870.jpg'," +
                    "'time': '30/04/2019',"+
                    "'theme': 'Politique',"+
                    "'themeAR': 'سياسة',"+
                    "'saved': false"+
                    "}," +
                    "{" +
                    "'id':2," +
                    "'title':'Bac 2019: une semaine d examen'," +
                    "'titleAR':'باك 2019: أسبوع من الفحص'," +
                    "'description': '743.000 candidats (en baisse de 1,27% par rapport à l an dernier, qui concernait la génération record des \"bébés an 2000\"), 174.300 correcteurs et examinateurs mobilisés, quatre millions de copies...: le bac est une étape majeure dans l année scolaire et pour les personnels de l Education nationale.\n" +
                    "\n" +
                    "Or des syndicats d enseignants ont appelé à la grève de la surveillance des épreuves du premier jour (philosophie pour les Terminales générales et technologiques, français pour les Premières), pour protester contre la réforme du bac.'," +
                    "'descriptionAR': '743000 من المتقدمين (بانخفاض 1.27 ٪ مقارنة بالعام الماضي ، والذي يتعلق بالجيل القياسي من \"سنة الطفل 2000\") ، حشد 174300 فاحص وفاحص ، أربعة ملايين نسخة ...: الدرج هو خطوة كبيرة في العام الدراسي وموظفي التعليم الوطني.\n" +
                    "ومع ذلك ، فقد دعت نقابات المعلمين إلى الإضراب للإشراف على اختبارات اليوم الأول (فلسفة المحطات العامة والتكنولوجية والفرنسية للأول) ، للاحتجاج على إصلاح الباكالوريا.'," +
                    "'main_image': 'https://9c998969b63acdb676d1-37595348221e1b716e1a6cfee3ed7891.ssl.cf1.rackcdn.com/almpics/2014/02/RTR22VZQ.jpg/RTR22VZQ-870.jpg'," +
                    "'time': '29/04/2019',"+
                    "'theme': 'Education',"+
                    "'themeAR': 'تعليم',"+
                    "'saved': false"+
                    "}," +
                    "{" +
                    "'id':3," +
                    "'title':'AJAX AMSTERDAM 1-1 JUVENTUS TURIN'," +
                    "'titleAR':'اجاكس امستردام 1-1 يوفنتوس'," +
                    "'description': 'LIGUE DES CHAMPIONS – On s attendait à un match intense entre deux historiques du football européen, ils nous ont servi. Si personne ne repart avec la victoire, tous les supporters peuvent être heureux de ce qu ils ont vu (1-1).'," +
                    "'descriptionAR': 'بطولات الدوري - كنا نتوقع مباراة قوية بين اثنين من كرة القدم الأوروبية التاريخية ، وأنها خدمتنا. إذا لم يغادر أحد النصر ، فسيكون كل المعجبين سعداء بما شاهدوه (1-1)'," +
                    "'main_image': 'https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf'," +
                    "'time': '29/04/2019',"+
                    "'theme': 'Sport',"+
                    "'themeAR': 'رياضة',"+
                    "'saved': false"+
                    "}," +
                    "{" +
                    "'id':4," +
                    "'title':'PSG: la Coupe de France pour éviter la saison noire'," +
                    "'titleAR':'باريس سان جيرمان: كوبيه دي فرانس لتجنب الموسم الأسود'," +
                    "'description': 'Saint-Denis (AFP) - La Coupe de France ne sauvera peut-être pas la saison du PSG, qui rêvait plus haut. Mais les Parisiens, avec le retour de Neymar comme titulaire samedi au Stade de France, doivent battre Rennes pour ne pas boucler l an I de l entraîneur Thomas Tuchel sur un échec.'," +
                    "'descriptionAR': 'سانت دينيس (أ ف ب) - قد لا ينقذ فريق Coupe de France موسم باريس سان جيرمان ، الذي كان يحلم بارتفاعه. لكن على الباريسيين ، مع عودة نيمار كحامل يوم السبت في استاد فرنسا ، أن يهزموا رين حتى لا يكتمل العام الذي أدير فيه توماس توشيل في حالة فشل'," +
                    "'main_image': 'https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf'," +
                    "'time': '29/04/2019',"+
                    "'theme': 'Sport',"+
                    "'themeAR': 'رياضة',"+
                    "'saved': false"+
                    "}," +
                    "{" +
                    "'id':5," +
                    "'title':'L ex-DGSN Hamel convoqué par la justice'," +
                    "'titleAR':'استدعى هامل سابقاً من قبل العدالة'," +
                    "'description': 'Abdelghani Hamel, ancien Directeur général de la sûreté nationale (DGSN) a été convoqué par la justice, annonce ce soir l’ENTV." +
                    "" +
                    "Selon la même source, le général à la retraite comparaitra lundi prochain en compagnie de son fils devant le juge d’instruction du tribunal de Tipaza. Ils sont poursuivis pour « activités illégales, trafic d’influence, détournement de foncier et mauvaise utilisation de la fonction », précise l ENTV.'," +
                    "'descriptionAR': 'عبد الغني هامل ، المدير العام السابق للأمن القومي" +
                    "" +
                    "وفقًا للمصدر نفسه ، سيظهر الجنرال المتقاعد يوم الاثنين المقبل مع ابنه أمام قاضي التحقيق في محكمة تيبازة. يقول ENTV إنهم يتعرضون للمحاكمة بتهمة أنشطة غير مشروعة ، والتأثير في بيع البنادق ، واختلاس الأراضي وإساءة استخدام الوظيفة.'," +
                    "'main_image': 'https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf'," +
                    "'time': '29/04/2019',"+
                    "'theme': 'Politique',"+
                    "'themeAR': 'سياسة',"+
                    "'saved': false"+
                    "}," +
                    "{" +
                    "'id':6," +
                    "'title':'Netflix annonce l arrivée de son nouveau jeu vidéo Stranger Things dans un mois'," +
                    "'titleAR':'تعلن Netflix عن وصول لعبة الفيديو الجديدة Stranger Things في شهر واحد'," +
                    "'description': 'Après l échec de son premier jeu Stranger Things sur mobile, Nefflix retente l aventure. Le titre sera cette fois disponible sur toutes les consoles, y compris la Nintendo Switch.\n" +
                    "Netflix a participé pour la première fois à l E3, le célèbre  salon du jeu vidéo organisé à Los Angeles. Le géant de la VOD (vidéo à la demande) a confirmé ses ambitions dans le secteur en dévoilant mercredi des titres tirés de séries originales, comme la série Stranger Things.'," +
                    "'descriptionAR': 'بعد فشل أول لعبة له Stranger Things على الهاتف المحمول ، حاول Nefflix المغامرة. سيكون العنوان متاحًا هذه المرة على جميع لوحات المفاتيح ، بما في ذلك Nintendo Switch\n" +
                    "شاركت Netflix لأول مرة في E3 ، عرض ألعاب الفيديو الشهير في لوس أنجلوس. أكدت شركة VOD العملاقة التي تعمل بالفيديو حسب الطلب طموحاتها في هذا القطاع من خلال الكشف عن ألقاب يوم الأربعاء من السلسلة الأصلية ، مثل سلسلة Stranger Things'," +
                    "'main_image': 'https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf'," +
                    "'time': '29/04/2019',"+
                    "'theme': 'Jeux',"+
                    "'themeAR': 'ألعاب',"+
                    "'saved': false"+
                    "}," +
                    "{" +
                    "'id':7," +
                    "'title':'Cacao : le Ghana et la Côte d Ivoire suspendent leurs ventes'," +
                    "'titleAR':'الكاكاو: غانا وساحل العاج تعلقان المبيعات'," +
                    "'description': 'À moins de 2600 dollars la tonne, on ne vend plus. C est l ultimatum qu ont lancé mercredi les deux plus gros producteurs mondiaux de cacao, la Côte d Ivoire et le Ghana. Une décision annoncée à l issue de deux jours de réunions entre producteurs, négociants et responsables politiques.\n" +
                    "« Ce qu il s est passé ces deux jours est historique », salue le directeur général du « Ghana cocoa Board », Joseph Boahen Aidoo. « Depuis des années, ce sont les acheteurs qui ont déterminé les prix ». Pour essayer d inverser la tendance, précise-t-il, « la Côte d Ivoire et le Ghana ont suspendu la vente des récoltes de 2020/2021 jusqu à nouvel ordre pour préparer la mise en place de ce prix minimum ».'," +
                    "'descriptionAR': 'بأقل من 2600 دولار للطن ، لم نعد نبيع. هذا هو الإنذار الأخير الذي أطلق يوم الأربعاء أكبر منتجين للكاكاو ، كوت ديفوار وغانا. تم الإعلان عن قرار بعد يومين من الاجتماعات بين المنتجين والتجار والسياسيين.\n" +
                    "\"ما حدث في هذين اليومين أمر تاريخي\" ، يحيي المدير العام لـ \"غانا كاكوا بورد\" جوزيف بواين إيدو. \"لسنوات ، حددت المشترين الأسعار.\" ويقول إنه لمحاولة قلب المد ، \"علقت كوت ديفوار وغانا بيع محاصيل 2020/2021 حتى إشعار آخر للتحضير لإدخال هذا السعر الأدنى\".'," +
                    "'main_image': 'https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf'," +
                    "'time': '29/04/2019',"+
                    "'theme': 'Economie',"+
                    "'themeAR': 'إقتصاد',"+
                    "'saved': false"+
                    "}" +
                    "]" +
                    "}"
            prefs.actualities = actualitiesData
        }
        renderDrawer()
        fetchJson()
    }
    fun renderDrawer(){
        var body = ""
        var prefs: Prefs? = null
        prefs = Prefs(this)
        if (prefs.contains("themes"))
            body = prefs.themes

        val gson = GsonBuilder().create()

        themeList = gson.fromJson(body, ThemeList::class.java)
        val filterdThemeList = themeList!!.themes.filter {  theme -> theme.checked }

        filterdThemeList.mapIndexed { index, theme ->
            val group = nav_view.menu.getItem(0).subMenu
            val item = group.add(theme.title)
            item.setIcon(R.drawable.ic_feed)

        }

    }
    fun fetchJson() {
        var prefs: Prefs? = null
        prefs = Prefs(this)

        var body = ""
        if (prefs.contains("actualities"))
            body = prefs.actualities
        val gson = GsonBuilder().create()




        var themeData = ""
        if (prefs.contains("themes"))
            themeData = prefs.themes

        themeList = gson.fromJson(themeData, ThemeList::class.java)

//        actualities = homeFeedAll!!.actualities.filter {
//            var theme = it.category
//            themeList!!.themes.filter { it.title == theme }.single().checked
//        } as ArrayList<Article>


        adapter  = MainAdapter(actualities, this)

        runOnUiThread {
            recyclerView_main.adapter = adapter!!
        }

    }

}



class Prefs (context: Context) {
    val PREFS_FILENAME = "com.prefs"
    val THEMES = "themes"
    val ACTUALITIES = "actualities"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    fun contains(keywork:String):Boolean{
        return prefs.contains(keywork)
    }
    var themes: String
        get() = prefs.getString(THEMES, "")
        set(value) = prefs.edit().putString(THEMES, value).apply()

    var actualities: String
        get() = prefs.getString(ACTUALITIES, "")
        set(value) = prefs.edit().putString(ACTUALITIES, value).apply()
}