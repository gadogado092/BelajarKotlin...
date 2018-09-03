package amat.belajarkotlin.main

import amat.belajarkotlin.NextTeam

interface NextView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<NextTeam>)
}