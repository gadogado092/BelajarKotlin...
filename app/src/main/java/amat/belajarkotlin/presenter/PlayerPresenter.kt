package amat.belajarkotlin.presenter

import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.TeamsApi
import amat.belajarkotlin.view.PlayerView
import amat.belajarkotlin.model.PlayerResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerPresenter (private val view: PlayerView,
                       private val apiRepository: ApiRepository,
                       private val gson: Gson) {

    fun getTeams(nameTeam : String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TeamsApi.getPlayers(nameTeam)),
                    PlayerResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.player)
            }
        }
    }



}