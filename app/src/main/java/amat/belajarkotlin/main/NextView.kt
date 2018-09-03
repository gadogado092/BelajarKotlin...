package amat.belajarkotlin.main

import amat.belajarkotlin.model.NextTeam

interface NextView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<NextTeam>)
}