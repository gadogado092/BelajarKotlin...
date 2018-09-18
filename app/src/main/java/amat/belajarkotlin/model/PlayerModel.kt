package amat.belajarkotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerModel (

        @SerializedName("strPlayer")
        var strPlayer: String? =null,

        @SerializedName("strCutout")
        var strCutout: String? =null,

        @SerializedName("strThumb")
        var strThumb: String? =null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? =null
        ) : Parcelable