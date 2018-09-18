package amat.belajarkotlin.dbsqlite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (val idEvent:String?,
                     val homeTeam: String?,
                     val awayTeam: String?,
                     val dateMatch: String?,
                     val timeMatch: String?,
                     val homeScore: String?,
                     val awayScore: String?,
                     val awayGoals: String?,
                     val awayGk: String?,
                     val awayMidfield: String?,
                     val awayDefense: String?,
                     val awayForward: String?,
                     val awaySubstitutes: String?,
                     val homeGoals: String?,
                     val homeGk: String?,
                     val homeMidfield: String?,
                     val homeDefense: String?,
                     val homeForward: String?,
                     val homeSubstitutes: String?
                        ) : Parcelable {

    companion object {
        const val TABLE_FAVORITE:String="TABLE_FAVORITE"
        const val ID_EVENT:String="ID_EVENT"
        const val HOME_TEAM:String="HOME_TEAM"
        const val AWAY_TEAM:String="AWAY_TEAM"
        const val DATE_MATCH:String="DATE_MATCH"
        const val TIME_MATCH:String="TIME_MATCH"
        const val HOME_SCORE:String="HOME_SCORE"
        const val AWAY_SCORE:String="AWAY_SCORE"
        const val AWAY_GOALS:String="AWAY_GOALS"
        const val AWAY_GK:String="AWAY_GK"
        const val AWAY_MIDFIELD:String="AWAY_MIDFIELD"
        const val AWAY_DEFENSE:String="AWAY_DEFENSE"
        const val AWAY_FORWARD:String="AWAY_FORWARD"
        const val AWAY_SUBTITUTES:String="AWAY_SUBTITUTES"
        const val HOME_GOALS:String="HOME_GOALS"
        const val HOME_GK:String="HOME_GK"
        const val HOME_MIDFIELD:String="HOME_MIDFIELD"
        const val HOME_DEFENSE:String="HOME_DEFENSE"
        const val HOME_FORWARD:String="HOME_FORWARD"
        const val HOME_SUBTITUTES:String="HOME_SUBTITUTES"
    }

}