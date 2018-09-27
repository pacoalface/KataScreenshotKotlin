package com.karumi.ui.presenter

import co.metalab.asyncawait.async
import com.karumi.common.weak
import com.karumi.domain.model.SuperHero
import com.karumi.domain.usecase.GetSuperHeroByName
import com.karumi.ui.LifecycleSubscriber

class SuperHeroDetailPresenter(
        view: View,
        private val getSuperHeroByName: GetSuperHeroByName) :
        LifecycleSubscriber {

    private val view: View? by weak(view)

    private lateinit var name: String

    fun preparePresenter(name: String?) {
        if (name != null) {
            this.name = name
        } else {
            view?.close()
        }
    }

    override fun update() {
        view?.showLoading()
        refreshSuperHeroes()
    }

    private fun refreshSuperHeroes() = async {
        try {
        val result = await { getSuperHeroByName(name) }
        view?.showSuperHero(result)
        } catch (e: Exception) {
            view?.showConnectionError()
        }
        view?.hideLoading()
    }

    interface View {
        fun close()
        fun showLoading()
        fun hideLoading()
        fun showSuperHero(superHero: SuperHero?)
        fun showConnectionError()
    }
}