package amat.belajarkotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamModel (

        @SerializedName("idTeam")
        var idTeam: String? =null,

        @SerializedName("strTeam")
        var strTeam: String? =null,

        @SerializedName("intFormedYear")
        var intFormedYear: String? =null,

        @SerializedName("strStadium")
        var strStadium: String? =null,

        @SerializedName("strStadiumThumb")
        var strStadiumThumb: String? =null,

        @SerializedName("strTeamBadge")
        var strTeamBadge: String? =null
        ) : Parcelable