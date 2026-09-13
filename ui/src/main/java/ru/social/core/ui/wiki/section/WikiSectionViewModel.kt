package ru.social.core.ui.wiki.section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.social.core.base.EventHandler
import ru.social.core.network.model.rpg.RpgClass
import ru.social.core.network.model.rpg.RpgRace
import ru.social.core.network.model.rpg.RpgTab
import ru.social.demo.services.retrofit.RpgApiClient
import ru.social.demo.utils.NetworkUtils
import javax.inject.Inject

@HiltViewModel
class WikiSectionViewModel @Inject constructor() : ViewModel(), EventHandler<WikiSectionContract.Event> {

    private val _compendiumState: MutableLiveData<WikiSectionContract.State> =
        MutableLiveData(WikiSectionContract.State(
            isClassesLoading = true,
            isRacesLoading = true,
            isMonstersLoading = true
        ))
    val compendiumState: LiveData<WikiSectionContract.State> = _compendiumState

    override fun handle(event: WikiSectionContract.Event) {
        when(event) {
            is WikiSectionContract.Event.LoadData<*> -> handleType(event)
            is WikiSectionContract.Event.Reload<*> -> handleType(event)
            is WikiSectionContract.Event.TabChanged -> handleTabChanged(event.idx)
            is WikiSectionContract.Event.ItemClicked<*> -> handleItemClick(event)
        }
    }


    private fun handleType(event: WikiSectionContract.Event.LoadData<*>) {
        when(event.type) {
            RpgTab.ALL -> viewModelScope.launch { fetchAll() }
            RpgTab.CLASS -> viewModelScope.launch { fetchClasses() }
            RpgTab.RACE -> viewModelScope.launch { fetchRaces() }
            RpgTab.MONSTER -> viewModelScope.launch { fetchMonsters() }
        }
    }

    private fun handleType(event: WikiSectionContract.Event.Reload<*>) {
        when(event.type) {
            RpgTab.ALL -> viewModelScope.launch { fetchAll(true) }
            RpgTab.CLASS -> viewModelScope.launch { fetchClasses(true) }
            RpgTab.RACE -> viewModelScope.launch { fetchRaces(true) }
            RpgTab.MONSTER -> viewModelScope.launch { fetchMonsters(true) }
        }
    }

    private fun handleTabChanged(idx: Int?) {
        _compendiumState.postValue(_compendiumState.value?.copy(selectedTab = idx ?: 0))
    }

    private fun handleItemClick(event: WikiSectionContract.Event.ItemClicked<*>) {
        when(event.type) {
            RpgTab.ALL -> throw NotImplementedError("Invalid type (ALL) for $event")
            RpgTab.CLASS -> _compendiumState.postValue(
                _compendiumState.value?.copy(bottomSheetItem = _compendiumState.value?.classes?.find { it.id == event.idx })
            )
            RpgTab.RACE -> _compendiumState.postValue(
                _compendiumState.value?.copy(bottomSheetItem = _compendiumState.value?.races?.find { it.id == event.idx })
            )
            RpgTab.MONSTER -> viewModelScope.launch { fetchMonster(event.idx, true) }
        }
    }

    private suspend fun fetchAll(isRefresh: Boolean = false) {
        fetchClasses(isRefresh)
        fetchRaces(isRefresh)
        fetchMonsters(isRefresh)
    }

    private suspend fun fetchClasses(isRefresh: Boolean = false) {
        if (isRefresh) {
            _compendiumState.postValue(_compendiumState.value?.copy(isClassesLoading = true))
        }

        CoroutineScope(Dispatchers.IO).launch {
            val result = mutableListOf<RpgClass>()

            RpgTab.CLASS.ids().forEach { id ->
                val res = RpgApiClient.service.getClassById(id)
                result.add(res)
            }
            _compendiumState.postValue(
                _compendiumState.value?.copy(classes = result, isClassesLoading = false)
            )
        }

    }

    private suspend fun fetchRaces(isRefresh: Boolean = false) {
        if (isRefresh) {
            _compendiumState.postValue(_compendiumState.value?.copy(isRacesLoading = true))
        }

        CoroutineScope(Dispatchers.IO).launch {
            val result = mutableListOf<RpgRace>()

            RpgTab.RACE.ids().forEach { id ->
                val res = RpgApiClient.service.getRaceById(id)
                result.add(res)
            }
            _compendiumState.postValue(
                _compendiumState.value?.copy(races = result, isRacesLoading = false)
            )
        }
    }

    private suspend fun fetchMonsters(isRefresh: Boolean = false) {
        if (isRefresh) {
            _compendiumState.postValue(_compendiumState.value?.copy(isMonstersLoading = true))
        }

        val result = NetworkUtils
            .makeCall { RpgApiClient.service.getMonsters() }
            .getOrNull()
        _compendiumState.postValue(
            _compendiumState.value?.copy(monsters = result?.results, isMonstersLoading = false)
        )
    }

    private suspend fun fetchMonster(id: String, isRefresh: Boolean = false) {
        if (isRefresh) {
            _compendiumState.postValue(_compendiumState.value?.copy(isMonsterLoading = true))
        }

        val result = NetworkUtils
            .makeCall { RpgApiClient.service.getMonsterById(id) }
            .getOrNull()
        _compendiumState.postValue(
            _compendiumState.value?.copy(bottomSheetItem = result, isMonsterLoading = false)
        )
    }

}