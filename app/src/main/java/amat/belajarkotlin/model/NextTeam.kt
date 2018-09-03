package amat.belajarkotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextTeam (

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

        @SerializedName("intAwayScore")
        var awayScore: String? =null

        ) : Parcelable