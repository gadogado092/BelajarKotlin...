package amat.belajarkotlin.model

import com.google.gson.annotations.SerializedName

data class LeagueModel (

        @SerializedName("idLeague")
        var idLeague: String? =null,
        @SerializedName("strLeague")
        var strLeague: String? =null
        )
