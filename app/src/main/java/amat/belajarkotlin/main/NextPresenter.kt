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

    fun getMatchNext() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(NextDBApi.getNextMatch("4332")),
                    NextResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.events)
            }
        }
    }
    fun getMatchLast() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(NextDBApi.getLastMatch("4332")),
                    NextResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.events)
            }
        }
    }

}