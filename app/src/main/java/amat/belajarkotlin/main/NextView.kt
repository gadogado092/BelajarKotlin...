package amat.belajarkotlin.main

import amat.belajarkotlin.model.NextTeam

interface NextView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<NextTeam>)
}