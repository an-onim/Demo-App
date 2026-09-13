package ru.social.demo

import ru.social.app.data.model.User
import ru.social.core.base.BaseEvent
import ru.social.core.base.BaseViewState

class MainContract {

    sealed interface Event : BaseEvent {
        object LoadUser : Event
        object Reload : Event
        object UserClicked : Event
        object UserRemoved : Event
    }

    sealed interface State : BaseViewState {
        data class SuccessUser(val data: User?) : State
        object LoadingUser : State
        object Error : State

    }

}