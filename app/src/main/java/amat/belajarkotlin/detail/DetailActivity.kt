package amat.belajarkotlin.detail

import amat.belajarkotlin.model.TeamsResponse

import amat.belajarkotlin.R
import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.MatchApi

import amat.belajarkotlin.dbsqlite.Favorite
import amat.belajarkotlin.dbsqlite.database
import amat.belajarkotlin.model.MatchModel
import android.app.Activity
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.uiThread
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : Activity() {
    private lateinit var match: MatchModel
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.getBundleExtra("myBundle")
        match  = bundle.getParcelable<MatchModel>("selected_match") as MatchModel
        id= match.idEvent.toString()
        val clubAway = findViewById<TextView>(R.id.awayClub)
        val clubHome = findViewById<TextView>(R.id.homeClub)
        val goalsAway = findViewById<TextView>(R.id.awayGoals)
        val gkAway = findViewById<TextView>(R.id.awayGk)
        val midfieldAway = findViewById<TextView>(R.id.awayMidfield)
        val defenseAway = findViewById<TextView>(R.id.awayDefense)
        val forwardAway = findViewById<TextView>(R.id.awayForward)
        val substitutesAway = findViewById<TextView>(R.id.awaySubstitutes)
        val goalsHome = findViewById<TextView>(R.id.homeGoals)
        val gkHome = findViewById<TextView>(R.id.homeGk)
        val midfieldHome = findViewById<TextView>(R.id.homeMidfield)
        val defenseHome = findViewById<TextView>(R.id.homeDefense)
        val forwardHome = findViewById<TextView>(R.id.homeForward)
        val substitutesHome = findViewById<TextView>(R.id.homeSubstitutes)
        val dateMatch =findViewById<TextView>(R.id.dateMatch)
        val timeMatch = findViewById<TextView>(R.id.timeMatch)
        val imageFavorite: ImageView = findViewById<ImageView>(R.id.imageFavorite)
        val vs = findViewById<TextView>(R.id.Vs)
        getTeamLogoHome(match.homeTeam)
        getTeamLogoAway(match.awayTeam)
        clubAway.text=match.awayTeam
        clubHome.text=match.homeTeam
        goalsHome.text=match.homeGoals
        gkHome.text=match.homeGk
        midfieldHome.text=match.homeMidfield
        defenseHome.text=match.homeDefense
        forwardHome.text=match.homeForward
        substitutesHome.text=match.homeSubstitutes

        goalsAway.text=match.awayGoals
        gkAway.text=match.awayGk
        midfieldAway.text=match.awayMidfield
        defenseAway.text=match.awayDefense
        forwardAway.text=match.awayForward
        substitutesAway.text=match.awaySubstitutes

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
                        Favorite.AWAY_SCORE to match.awayScore,
                        Favorite.AWAY_GOALS to match.awayGoals,
                        Favorite.AWAY_GK to match.awayGk,
                        Favorite.AWAY_MIDFIELD to match.awayMidfield,
                        Favorite.AWAY_DEFENSE to match.awayDefense,
                        Favorite.AWAY_FORWARD to match.awayForward,
                        Favorite.AWAY_SUBTITUTES to match.awaySubstitutes,
                        Favorite.HOME_GOALS to match.homeGoals,
                        Favorite.HOME_GK to match.homeGk,
                        Favorite.HOME_MIDFIELD to match.homeMidfield,
                        Favorite.HOME_DEFENSE to match.homeDefense,
                        Favorite.HOME_FORWARD to match.homeForward,
                        Favorite.HOME_SUBTITUTES to match.homeSubstitutes
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
                    .doRequest(MatchApi.getTeamsLogo(id)),
                    TeamsResponse::class.java
            )

            uiThread {

                Picasso.get().load(data.teams?.get(0)?.strTeamBadge).into(homeBadage)
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
                    .doRequest(MatchApi.getTeamsLogo(id)),
                    TeamsResponse::class.java
            )

            uiThread {

                Picasso.get().load(data.teams?.get(0)?.strTeamBadge).into(awayBadage)

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
