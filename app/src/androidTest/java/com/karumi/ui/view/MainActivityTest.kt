package com.karumi.ui.view

import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.karumi.data.repository.SuperHeroRepository
import com.karumi.domain.model.SuperHero
import com.karumi.mockito.MockitoExtensions.on
import org.junit.Test
import org.mockito.Mock

class MainActivityTest : AcceptanceTest<MainActivity>(MainActivity::class.java) {

    @Mock private lateinit var repository: SuperHeroRepository

    @Test
    fun showsEmptyCaseIfThereAreNoSuperHeroes() {
        givenThereAreNoSuperHeroes()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test fun showsAnyNumberOfSuperHeroes() {
        givenThereAreSomeSuperHeroes(10)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test fun showsTwoAvengers() {
        givenThereAreSomeSuperHeroes(2, true)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test fun showsRegularSuperHero() {
        givenThereAreSomeSuperHeroes(1, false)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test fun showsRegularSuperHeroWithLongName() {
        givenASuperHeroWithALongName()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test fun showsRegularSuperHeroWithoutName() {
        givenASuperHeroWithoutName()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test fun showsAvengerSuperHeroWithLongName() {
        givenASuperHeroWithALongName()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test fun showsAvengerSuperHeroWithoutName() {
        givenASuperHeroWithoutName()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    private fun givenASuperHeroWithALongName(isAvenger : Boolean = false) {
        val superHero = SuperHero("A long name to test the textview ellipsis or die trying", null, isAvenger,
            "lorem ipsum")
        on(repository.getAllSuperHeroes()).thenReturn(arrayListOf(superHero))
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