package amat.belajarkotlin.api

import amat.belajarkotlin.BuildConfig
import android.net.Uri

object NextDBApi {
    fun getTeams (idLeague: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id",idLeague)
                .build()
                .toString()
    }
    fun getTeamsLast (idLeague: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id",idLeague)
                .build()
                .toString()
    }
    fun getTeamsLogo (idLogo: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("searchteams.php")
                .appendQueryParameter("t",idLogo)
                .build()
                .toString()
    }
}