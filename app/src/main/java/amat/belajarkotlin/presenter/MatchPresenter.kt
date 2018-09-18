package amat.belajarkotlin.presenter

import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.MatchApi
import amat.belajarkotlin.view.MatchView
import amat.belajarkotlin.model.MatchResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter (private val view: MatchView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson) {

    fun getMatchNext(idLeague : String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(MatchApi.getNextMatch(idLeague)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.events,true)
            }
        }
    }
    fun getMatchLast(idLeague : String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(MatchApi.getLastMatch(idLeague)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.events,true)
            }
        }
    }
    fun searchMatch(Key : String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(MatchApi.searchMatch(Key)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                if (data.event == null){
                    view.showMatchList(emptyList(),false)
                }else{
                    view.showMatchList(data.event,true)
                }

            }
        }
    }

}