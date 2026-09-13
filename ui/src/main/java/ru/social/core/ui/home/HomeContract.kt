package ru.social.core.ui.home

import ru.social.core.base.BaseEvent
import ru.social.core.base.BaseViewState
import ru.social.core.network.model.Post

class HomeContract {

    sealed interface Event : BaseEvent {
        object LoadFeed : Event
        object Reload : Event
        data class EditPostClicked(val post: Post?) : Event
        object UserClicked : Event
    }

    sealed interface State : BaseViewState {
        data class SuccessFeed(val data: List<Post>) : State
        object LoadingFeed : State

        data class PostToEdit(val data: Post?) : State

        object Error : State
    }

}