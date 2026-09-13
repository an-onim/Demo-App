package ru.social.app.data.model.rpg

import java.util.Locale

enum class RpgTab {
    ALL {
        override fun ids(): List<String> = emptyList()
    },

    CLASS {
        override fun ids(): List<String> = listOf(
            "barbarian", "bard", "cleric",
            "druid", "fighter", "monk",
            "paladin", "ranger", "rogue",
            "sorcerer", "warlock", "wizard"
        )
    },

    RACE {
        override fun ids(): List<String> = listOf(
            "dragonborn", "dwarf", "elf",
            "gnome", "half-elf", "half-orc",
            "halfling", "human", "tiefling"
        )
    },

    MONSTER {
        override fun ids(): List<String> = emptyList()
    };


    fun label(): String = this.toString().lowercase().replaceFirstChar { it.titlecase(Locale.ROOT) }

    abstract fun ids(): List<String>
}