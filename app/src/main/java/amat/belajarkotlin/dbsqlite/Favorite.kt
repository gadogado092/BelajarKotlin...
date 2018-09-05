package amat.belajarkotlin.dbsqlite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (val idEvent:String?,val homeTeam: String?,val awayTeam: String?,val dateMatch: String?,val timeMatch: String?,val homeScore: String?,val awayScore: String?) : Parcelable {
    companion object {
        const val TABLE_FAVORITE:String="TABLE_FAVORITE"
        const val ID_EVENT:String="ID_EVENT"
        const val HOME_TEAM:String="HOME_TEAM"
        const val AWAY_TEAM:String="AWAY_TEAM"
        const val DATE_MATCH:String="DATE_MATCH"
        const val TIME_MATCH:String="TIME_MATCH"
        const val HOME_SCORE:String="HOME_SCORE"
        const val AWAY_SCORE:String="AWAY_SCORE"
    }

}