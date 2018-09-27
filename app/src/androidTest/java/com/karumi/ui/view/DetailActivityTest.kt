package com.karumi.ui.view

import android.os.Bundle
import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.karumi.data.repository.SuperHeroRepository
import com.karumi.domain.model.SuperHero
import com.karumi.mockito.MockitoExtensions.on
import org.junit.Test
import org.mockito.Mock

class DetailActivityTest : AcceptanceTest<SuperHeroDetailActivity>(SuperHeroDetailActivity::class.java) {

    @Mock private lateinit var repository: SuperHeroRepository

    @Test
    fun showsAvenger() {
        val bundle = givenBundle("Spiderman")
        givenAnAvenger()

        val activity = startActivity(bundle)

        compareScreenshot(activity)
    }

    @Test
    fun showsSuperHeroWithLongName() {
        val bundle = givenBundle("Spiderman")
        givenASuperHeroWithALongName()

        val activity = startActivity(bundle)

        compareScreenshot(activity)
    }

    @Test
    fun showsAvengerWithLongName() {
        val bundle = givenBundle("Spiderman")
        givenASuperHeroWithALongName(true)

        val activity = startActivity(bundle)

        compareScreenshot(activity)
    }

    @Test
    fun showsSuperHeroNotFound() {
        val bundle = givenBundle("Spiderman")
        givenSuperHeroNotFound()

        val activity = startActivity(bundle)

        compareScreenshot(activity)
    }

    @Test
    fun showsInternetError() {
        val bundle = givenBundle("Spiderman")
        givenNoInternetConnectionError()

        val activity = startActivity(bundle)

        compareScreenshot(activity)
    }

    private fun givenNoInternetConnectionError() {
        on(repository.getByName("Spiderman")).thenThrow(Exception::class.java)
    }

    private fun givenBundle(name: String) : Bundle{
        return Bundle().apply {
            putString(SuperHeroDetailActivity.SUPER_HERO_NAME_KEY, name)
        }
    }

    private fun givenASuperHeroWithALongName(isAvenger : Boolean = false) {
        val superHero = SuperHero("A long name to test the textview ellipsis or die trying", null, isAvenger,
            "lorem ipsum")
        on(repository.getByName("Spiderman")).thenReturn(superHero)
    }

    private fun givenSuperHeroNotFound() {
        on(repository.getByName("Spiderman")).thenReturn(null)
    }

    private fun givenAnAvenger() {
        val superHero = SuperHero("", null, true, "lorem ipsum")
        on(repository.getByName("Spiderman")).thenReturn(superHero)
    }

    private fun givenThereAreNoSuperHeroes() {
        on(repository.getAllSuperHeroes()).thenReturn(emptyList())
    }

    override val testDependencies = Module(allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}