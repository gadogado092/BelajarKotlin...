package amat.belajarkotlin.view

import amat.belajarkotlin.model.PlayerModel


interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<PlayerModel>)
}