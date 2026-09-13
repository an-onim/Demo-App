package ru.social.core.ui.user.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.social.core.base.EventHandler
import ru.social.core.base.store.SharedPrefs
import ru.social.core.network.model.User
import ru.social.demo.services.AuthClient
import ru.social.demo.services.FirestoreClient
import ru.social.demo.services.FsPath
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs
): ViewModel(), EventHandler<AuthContract.Event> {

    private val _userState: MutableLiveData<AuthContract.State> = MutableLiveData(AuthContract.State())
    val userState: LiveData<AuthContract.State> = _userState

    init {
        sharedPrefs.clearUserId()
        sharedPrefs.setIsHost(false)
    }

    override fun handle(event: AuthContract.Event) {
        when(event) {
            is AuthContract.Event.SignInClicked -> handleSignIn(event)
            is AuthContract.Event.SignInHostClicked -> handleSignInHost(event)
            is AuthContract.Event.SignUpToggle -> handleSignUp()
            is AuthContract.Event.FinishSignUpClicked -> handleFinishSignUp(event)
        }
    }

    private fun handleSignIn(event: AuthContract.Event.SignInClicked) {
        _userState.postValue(_userState.value?.copy(error = ""))

        _userState.value?.let { state ->
            AuthClient.getInstance().signIn(
                state.email, state.password,
                onSuccess = {
                    if (it?.uid.isNullOrBlank()) {
                        _userState.postValue(_userState.value?.copy(error = "Wrong user id"))
                    } else {
                        sharedPrefs.setUserId(it?.uid)
                        event.onSuccess.invoke()
                    }
                },
                onError = { _userState.postValue(_userState.value?.copy(error = it)) }
            )
        }

    }

    private fun handleSignInHost(event: AuthContract.Event.SignInHostClicked) {
        sharedPrefs.setIsHost(true)
        event.onSuccess.invoke()
    }

    private fun handleSignUp() {
        val flag = _userState.value?.isSignUp ?: true
        _userState.postValue(_userState.value?.copy(isSignUp = !flag))
    }

    private fun handleFinishSignUp(event: AuthContract.Event.FinishSignUpClicked) {
        _userState.postValue(_userState.value?.copy(error = ""))

        _userState.value?.let { state ->
            AuthClient.getInstance().signUp(
                state.email, state.password,
                onSuccess = { user ->
                    if (user == null) {
                        _userState.postValue(_userState.value?.copy(error = "Can't sign up user"))
                        return@signUp
                    }

                    sharedPrefs.setUserId(user.uid)

                    FirestoreClient.getInstance().setData(
                        FsPath.USERS,
                        id = user.uid,
                        data = User(
                            id = user.uid,
                            name = state.name,
                            imageUrl = state.imageUrl
                        ),
                        onError = { _userState.postValue(_userState.value?.copy(error = it)) }
                    )

                    event.onSuccess.invoke()
                },
                onError = { _userState.postValue(_userState.value?.copy(error = it)) }
            )
        }

    }


    fun onEmailChanged(str: String) {
        _userState.postValue(_userState.value?.copy(email = str))
    }

    fun onPasswordChanged(str: String) {
        _userState.postValue(_userState.value?.copy(password = str))
    }

    fun onNameChanged(str: String) {
        _userState.postValue(_userState.value?.copy(name = str))
    }

    fun onImageUrlChanged(str: String) {
        _userState.postValue(_userState.value?.copy(imageUrl = str))
    }

}