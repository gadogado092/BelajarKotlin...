package amat.belajarkotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchModel (

        @SerializedName("idEvent")
        var idEvent: String? =null,

        @SerializedName("strHomeTeam")
        var homeTeam: String? =null,

        @SerializedName("strAwayTeam")
        var awayTeam: String? =null,

        @SerializedName("dateEvent")
        var dateMatch: String? =null,

        @SerializedName("strTime")
        var timeMatch: String? =null,

       @SerializedName("intHomeScore")
        var homeScore: String? =null,

        @SerializedName("strHomeGoalDetails")
        var homeGoals: String? =null,

        @SerializedName("strAwayGoalDetails")
        var awayGoals: String? =null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeGk: String? =null,

        @SerializedName("strAwayLineupGoalkeeper")
        var awayGk: String? =null,

        @SerializedName("strHomeLineupDefense")
        var homeDefense: String? =null,

        @SerializedName("strAwayLineupDefense")
        var awayDefense: String? =null,

        @SerializedName("strHomeLineupMidfield")
        var homeMidfield: String? =null,

        @SerializedName("strAwayLineupMidfield")
        var awayMidfield: String? =null,

        @SerializedName("strHomeLineupForward")
        var homeForward: String? =null,

        @SerializedName("strAwayLineupForward")
        var awayForward: String? =null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeSubstitutes: String? =null,

        @SerializedName("strAwayLineupSubstitutes")
        var awaySubstitutes: String? =null,


        @SerializedName("intAwayScore")
        var awayScore: String? =null


        ) : Parcelable