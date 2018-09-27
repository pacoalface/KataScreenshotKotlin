package com.karumi.ui.view

import com.karumi.domain.model.SuperHero

class SuperHeroesMotherObject {

    companion object {
        fun getSuperHero(isAvenger: Boolean = false): SuperHero = SuperHero("Spiderman", "www.photopic.com/spiderman",
            isAvenger, "lorem ipsum")

        fun getSuperHeroes(): List<SuperHero> = arrayListOf(getSuperHero(), getSuperHero(), getSuperHero())
        fun getAvengers(): List<SuperHero> = arrayListOf(getSuperHero(true), getSuperHero(true), getSuperHero(true),
            getSuperHero(true), getSuperHero(true), getSuperHero(true), getSuperHero(true), getSuperHero(true),
            getSuperHero(true))
    }
}