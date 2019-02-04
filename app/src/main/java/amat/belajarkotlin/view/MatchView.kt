package amat.belajarkotlin.view

import amat.belajarkotlin.model.MatchModel

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<MatchModel>?, condition:Boolean)
}