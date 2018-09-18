package amat.belajarkotlin.view

import amat.belajarkotlin.model.TeamModel

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<TeamModel>,condition:Boolean)
}