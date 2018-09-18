package amat.belajarkotlin.dbsqlite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteTeam (val idTeam:String?,
                         val strTeam: String?,
                         val intFormedYear: String?,
                         val strStadium: String?,
                         val strStadiumThumb: String?,
                         val teamBadge: String?
                        ) : Parcelable {

    companion object {
        const val TABLE_FAVORITE:String="TABLE_FAVORITE_TEAM"
        const val ID_TEAM:String="ID_TEAM"
        const val STR_TEAM:String="STR_TEAM"
        const val INT_FORMEDYEAR:String="INT_FORMEDYEAR"
        const val STR_STADIUM:String="STR_STADIUM"
        const val STR_STADIUMTHUMB:String="STR_STADIUMTHUMB"
        const val TEAM_BADGE:String="HOME_SCORE"

    }

}