package amat.belajarkotlin

import amat.belajarkotlin.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}