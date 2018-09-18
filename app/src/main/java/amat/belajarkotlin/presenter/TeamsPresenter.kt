package amat.belajarkotlin.presenter

import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.TeamsApi
import amat.belajarkotlin.view.TeamsView
import amat.belajarkotlin.model.TeamsResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter (private val view: TeamsView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson) {

    fun getTeams(nameLeague : String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TeamsApi.getTeams(nameLeague)),
                    TeamsResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.teams,true)
            }
        }
    }

    fun searchTeam(nameLeague : String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TeamsApi.searchTeam(nameLeague)),
                    TeamsResponse::class.java
            )

            uiThread {
                view.hideLoading()
                if (data.teams == null){
                    view.showMatchList(emptyList(),false)
                }else{
                    view.showMatchList(data.teams,true)
                }
            }
        }
    }



}