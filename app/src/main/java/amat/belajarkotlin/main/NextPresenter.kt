package amat.belajarkotlin.main

import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.NextDBApi
import amat.belajarkotlin.model.NextResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextPresenter (private val view: NextView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson) {

    fun getTeamList() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(NextDBApi.getTeams("4332")),
                    NextResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.events)
            }
        }
    }
    fun getTeamListLast() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(NextDBApi.getTeamsLast("4332")),
                    NextResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.events)
            }
        }
    }

}