package ru.social.core.ui.wiki.section

import ru.social.core.base.BaseEvent
import ru.social.core.base.BaseViewState
import ru.social.core.network.model.rpg.RpgClass
import ru.social.core.network.model.rpg.RpgRace
import ru.social.core.network.model.rpg.RpgTab
import ru.social.core.network.model.rpg.ShortInfo

class WikiSectionContract {

    sealed interface Event : BaseEvent {
        data class LoadData<T>(val type: T): Event
        data class Reload<T>(val type: T): Event
        data class TabChanged(val idx: Int): Event
        data class ItemClicked<T>(val type: T, val idx: String): Event
    }

    data class State(
        val selectedTab: Int = 0,
        val bottomSheetItem: Any? = null,
        val classes: List<RpgClass> = emptyList(),
        val races: List<RpgRace> = emptyList(),
        val monsters: List<ShortInfo>? = null,
        val isClassesLoading: Boolean = false,
        val isRacesLoading: Boolean = false,
        val isMonstersLoading: Boolean = false,
        val isMonsterLoading: Boolean = false,
        val isError: Boolean = false
    ): BaseViewState {

        fun isEmptyState() =
            classes.isEmpty() && races.isEmpty() && monsters.isNullOrEmpty()

        fun isDataEmpty(type: RpgTab) = when(type) {
            RpgTab.CLASS -> classes.isEmpty()
            RpgTab.RACE -> races.isEmpty()
            RpgTab.MONSTER -> monsters.isNullOrEmpty()
            else -> false
        }

    }

}