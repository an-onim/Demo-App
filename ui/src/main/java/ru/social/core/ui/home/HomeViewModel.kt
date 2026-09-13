package ru.social.core.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.social.core.base.EventHandler
import ru.social.core.base.NetworkUtils
import ru.social.core.network.model.Post
import ru.social.demo.services.FirestoreClient
import ru.social.demo.services.FsPath
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel(), EventHandler<HomeContract.Event> {

    private val _postsViewState: MutableLiveData<HomeContract.State> = MutableLiveData(HomeContract.State.LoadingFeed)
    val postsViewState: LiveData<HomeContract.State> = _postsViewState

    private val _postToEditState: MutableLiveData<HomeContract.State> = MutableLiveData(HomeContract.State.PostToEdit(null))
    val postToEditState: LiveData<HomeContract.State> = _postToEditState

    override fun handle(event: HomeContract.Event) {
        when(event) {
            is HomeContract.Event.LoadFeed -> handlePostsState(event)
            is HomeContract.Event.Reload -> viewModelScope.launch { fetchPosts(true) }
            is HomeContract.Event.EditPostClicked -> onPostEditClick(event)
            is HomeContract.Event.UserClicked -> onUserClick()
        }
    }

    private fun handlePostsState(event: HomeContract.Event) {
        when(_postsViewState.value) {
            is HomeContract.State.SuccessFeed -> {}
            is HomeContract.State.LoadingFeed -> viewModelScope.launch { fetchPosts() }
            is HomeContract.State.Error -> handleError(event)
            else -> handleError(event)
        }
    }

    private fun handleError(event: HomeContract.Event) {
        when(event) {
            HomeContract.Event.LoadFeed -> viewModelScope.launch { fetchPosts(isRefresh = true) }
            else -> throw NotImplementedError("Invalid event ($event) for ViewState.Loading")
        }
    }

    private suspend fun fetchPosts(isRefresh: Boolean = false) {
        if (isRefresh) {
            _postsViewState.postValue(HomeContract.State.LoadingFeed)
        }

        NetworkUtils.makeCall {
            FirestoreClient.getInstance().readData<Post>(
                path = FsPath.POSTS,
                onSuccess = { result ->
                    val data = result?.sortedByDescending { it.createDate } ?: emptyList()
                    _postsViewState.postValue(HomeContract.State.SuccessFeed(data = data))
                },
                onError = {
                    _postsViewState.postValue(HomeContract.State.Error)
                }
            )
        }

    }

    private fun onPostEditClick(event: HomeContract.Event.EditPostClicked) {
        _postToEditState.postValue(HomeContract.State.PostToEdit(event.post))
    }

    private fun onUserClick() {
        // TODO: to profile of author of post
    }

}
