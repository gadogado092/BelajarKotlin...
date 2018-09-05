package amat.belajarkotlin.detail

import amat.belajarkotlin.model.LogoResponse
import amat.belajarkotlin.model.NextTeam
import amat.belajarkotlin.R
import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.NextDBApi
import amat.belajarkotlin.dbsqlite.Favorite
import amat.belajarkotlin.dbsqlite.database
import android.app.Activity
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.design_layout_snackbar_include.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.uiThread
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

class DetailActivityFavorite : Activity() {
    private lateinit var match: Favorite
    private var isFavorite: Boolean = false
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.getBundleExtra("myBundle")
        match  = bundle.getParcelable<Favorite>("selected_match") as Favorite
        id= match.idEvent.toString()
        val clubAway = findViewById<TextView>(R.id.awayClub)
        val clubHome = findViewById<TextView>(R.id.homeClub)
        val dateMatch =findViewById<TextView>(R.id.dateMatch)
        val timeMatch = findViewById<TextView>(R.id.timeMatch)
        val imageFavorite: ImageView = findViewById<ImageView>(R.id.imageFavorite)
        val vs = findViewById<TextView>(R.id.Vs)
        getTeamLogoHome(match.homeTeam)
        getTeamLogoAway(match.awayTeam)
        clubAway.text=match.awayTeam
        clubHome.text=match.homeTeam
        dateMatch.text=parseDateToView(match.dateMatch.toString())
        timeMatch.text=match.timeMatch.toString().substring(0, 5)
        if (match.awayScore!=null){
            vs.text=match.homeScore+"\t\tVS\t\t"+match.awayScore
        }

        imageFavorite.onClick {
            if (isFavorite){
                removeFromFavorite()
            }else{
                addFavorite()
            }
            isFavorite=!isFavorite
            setFavorite()
        }

        favoriteState()
        setFavorite()
    }

    private fun addFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.ID_EVENT to match.idEvent,
                        Favorite.HOME_TEAM to match.homeTeam,
                        Favorite.AWAY_TEAM to match.awayTeam,
                        Favorite.DATE_MATCH to match.dateMatch,
                        Favorite.TIME_MATCH to match.timeMatch,
                        Favorite.HOME_SCORE to match.homeScore,
                        Favorite.AWAY_SCORE to match.awayScore
                )
            }
            Toast.makeText(this,"Added to favorite",Toast.LENGTH_SHORT).show()
        }catch (e:SQLiteConstraintException){

        }
    }

    private fun removeFromFavorite(){

        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                        "id" to id )
            }
            Toast.makeText(this,"Remove from favorite",Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(ID_EVENT = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            imageFavorite.setImageResource(R.drawable.ic_favorite_black_24dp)
        else
            imageFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
    }

    private fun getTeamLogoHome(id:String?) {
        val gson = Gson()
        val apiRepository = ApiRepository()
        doAsync {

            val data = gson.fromJson(apiRepository
                    .doRequest(NextDBApi.getTeamsLogo(id)),
                    LogoResponse::class.java
            )

            uiThread {

                Picasso.get().load(data.teams.get(0).teamBadge).into(homeBadage)
                //view.hideLoading()
                //view.showTeamList(data.events)
            }
        }
    }
    private fun getTeamLogoAway(id:String?) {
        val gson = Gson()
        val apiRepository = ApiRepository()
        doAsync {

            val data = gson.fromJson(apiRepository
                    .doRequest(NextDBApi.getTeamsLogo(id)),
                    LogoResponse::class.java
            )

            uiThread {

                Picasso.get().load(data.teams.get(0).teamBadge).into(awayBadage)

            }
        }
    }
    private fun parseDateToView(time: String): String? {
        val inputPattern = "yyyy-MM-dd"
        val outputPattern = "EEE-d-MM-yyyy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }
}
