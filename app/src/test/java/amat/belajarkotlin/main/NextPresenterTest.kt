package amat.belajarkotlin.main

import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.NextDBApi
import amat.belajarkotlin.model.NextResponse
import amat.belajarkotlin.model.NextTeam

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import org.junit.Test


import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NextPresenterTest {

    @Mock
    private
    lateinit var view: NextView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: NextPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextPresenter(view, apiRepository, gson)
    }

    @Test
    fun getMatchNext() {

        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(NextDBApi.getNextMatch("4332")),
                    NextResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }

    }
}