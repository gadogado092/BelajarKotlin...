package amat.belajarkotlin.detail

import amat.belajarkotlin.model.LogoResponse
import amat.belajarkotlin.model.NextTeam
import amat.belajarkotlin.R
import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.NextDBApi
import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.getBundleExtra("myBundle")
        val match  = bundle.getParcelable<NextTeam>("selected_match") as NextTeam

        val clubAway = findViewById<TextView>(R.id.awayClub)
        val clubHome = findViewById<TextView>(R.id.homeClub)
        val dateMatch =findViewById<TextView>(R.id.dateMatch)
        val timeMatch = findViewById<TextView>(R.id.timeMatch)
        val vs = findViewById<TextView>(R.id.Vs)
        getTeamLogohome(match.homeTeam)
        getTeamLogoaway(match.awayTeam)
        clubAway.text=match.awayTeam
        clubHome.text=match.homeTeam
        dateMatch.text=parseDatetoview(match.dateMatch.toString())
        timeMatch.text=match.timeMatch.toString().substring(0, 5)
        if (match.awayScore!=null){
            vs.text=match.homeScore+"\t\tVS\t\t"+match.awayScore
        }

    }

    private fun getTeamLogohome(id:String?) {
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
    private fun getTeamLogoaway(id:String?) {
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
    private fun parseDatetoview(time: String): String? {
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
