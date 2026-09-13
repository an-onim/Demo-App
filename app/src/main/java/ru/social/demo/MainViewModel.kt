package ru.social.demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.social.app.data.model.User
import ru.social.app.data.network.firebase.FirestoreClient
import ru.social.app.data.network.firebase.FsPath
import ru.social.core.base.NetworkUtils
import ru.social.core.base.EventHandler
import ru.social.core.base.store.SharedPrefs
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs
): ViewModel(), EventHandler<MainContract.Event> {

    private val _userViewState: MutableLiveData<MainContract.State> = MutableLiveData(MainContract.State.LoadingUser)
    val userViewState: LiveData<MainContract.State> = _userViewState

    override fun handle(event: MainContract.Event) {
        when(event) {
            is MainContract.Event.LoadUser -> handleUserState(event)
            is MainContract.Event.Reload -> viewModelScope.launch { fetchUser() }
            is MainContract.Event.UserClicked -> {}
            is MainContract.Event.UserRemoved -> clearUser()
        }
    }

    private fun handleUserState(event: MainContract.Event) {
        when(val currentState = _userViewState.value) {
            is MainContract.State.SuccessUser -> {
                when(event) {
                    is MainContract.Event.LoadUser -> viewModelScope.launch { fetchUser() }
                    is MainContract.Event.UserClicked -> {}
                    else -> throw NotImplementedError("Invalid event ($event) for state $currentState")
                }
            }
            is MainContract.State.LoadingUser -> viewModelScope.launch { fetchUser() }
            is MainContract.State.Error -> viewModelScope.launch { fetchUser() }
            else -> throw NotImplementedError("Invalid event ($event) for $currentState")
        }
    }

    private suspend fun fetchUser() {
        NetworkUtils.makeCall {
            val userId = sharedPrefs.getUserId()
            if (userId.isNullOrBlank()) {
                _userViewState.postValue(MainContract.State.Error)
                return@makeCall
            }

            Log.d("TEST", "MainVM fetchUser isHost = ${sharedPrefs.isHost()}")
            if (sharedPrefs.isHost()) {
                _userViewState.postValue(MainContract.State.SuccessUser(data = null))
                return@makeCall
            }

            FirestoreClient.getInstance().readData<User>(
                path = FsPath.USERS,
                docId = userId,
                onSuccess = { result ->
                    _userViewState.postValue(MainContract.State.SuccessUser(data = result))
                },
                onError = {
                    _userViewState.postValue(MainContract.State.Error)
                }
            )
        }
    }

    private fun clearUser() {
        sharedPrefs.clearUserId()
        sharedPrefs.setIsHost(false)
    }

}