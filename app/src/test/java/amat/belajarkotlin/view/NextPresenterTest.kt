package amat.belajarkotlin.view

import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.presenter.MatchPresenter
import com.google.gson.Gson
import org.junit.Test


import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NextPresenterTest {

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson)
    }

    @Test
    fun getMatchNext() {



    }
}