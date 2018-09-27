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

    private fun givenASuperHeroWithoutName(isAvenger : Boolean = false) {
        val superHero = SuperHero("", null, isAvenger, "lorem ipsum")
        on(repository.getAllSuperHeroes()).thenReturn(arrayListOf(superHero))
    }

    private fun givenThereAreSomeSuperHeroes(
        numberOfSuperHeroes: Int = 1,
        avengers: Boolean = false): List<SuperHero> {
        val superHeroes = IntRange(0, numberOfSuperHeroes - 1).map { id ->
            val superHeroName = "SuperHero - " + id
            val superHeroDescription = "Description Super Hero - " + id
            SuperHero(superHeroName, null, avengers,
                superHeroDescription)
        }

        on(repository.getAllSuperHeroes()).thenReturn(superHeroes)
        return superHeroes
    }

    private fun givenThereAreNoSuperHeroes() {
        on(repository.getAllSuperHeroes()).thenReturn(emptyList())
    }

    override val testDependencies = Module(allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}