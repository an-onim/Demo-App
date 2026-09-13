package ru.social.core.ui.user.auth

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.social.demo.MainContract
import ru.social.demo.MainViewModel
import ru.social.demo.R
import ru.social.demo.ui.components.buttons.CButton
import ru.social.demo.ui.components.buttons.CTextButton
import ru.social.demo.ui.components.text.RoundedTextField
import ru.social.demo.ui.theme.SDTheme

@Composable
fun AuthPage(
    goHomePage: () -> Unit,
    viewModel: AuthViewModel
) {
    val mainViewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)

    val userState = viewModel.userState.observeAsState()
    val isSignUp = userState.value?.isSignUp == true
    val error = userState.value?.error

    Scaffold(
        containerColor = SDTheme.colors.bgSecondary
    ) { insets ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(insets),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null
            )

            Column(
                Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.app_greeting),
                    style = SDTheme.typography.headingM,
                    color = SDTheme.colors.fgPrimary
                )

                RoundedTextField(
                    value = userState.value?.email ?: "",
                    hint = stringResource(R.string.email),
                    required = isSignUp,
                    onValueChange = viewModel::onEmailChanged
                )

                RoundedTextField(
                    value = userState.value?.password ?: "",
                    hint = stringResource(R.string.password),
                    required = isSignUp,
                    onValueChange = viewModel::onPasswordChanged
                )

                AnimatedVisibility(
                    visible = isSignUp
                ) {
                    RoundedTextField(
                        value = userState.value?.name ?: "",
                        hint = stringResource(R.string.name),
                        required = true,
                        onValueChange = viewModel::onNameChanged
                    )
                }

                AnimatedVisibility(
                    visible = isSignUp
                ) {
                    RoundedTextField(
                        value = userState.value?.imageUrl ?: "",
                        hint = stringResource(R.string.image_url),
                        onValueChange = viewModel::onImageUrlChanged
                    )
                }

                AnimatedVisibility(
                    visible = !error.isNullOrBlank()
                ) {
                    Text(
                        error ?: "",
                        style = SDTheme.typography.caption,
                        color = colorResource(R.color.error),
                        textAlign = TextAlign.Center
                    )
                }

                AnimatedRow(
                    visible = !isSignUp
                ) {
                    CButton(
                        label = stringResource(R.string.signIn),
                        enabled = userState.value?.isValid() == true
                    ) {
                        viewModel.handle(AuthContract.Event.SignInClicked(
                            onSuccess = {
                                if (error.isNullOrBlank()) {
                                    mainViewModel.handle(MainContract.Event.LoadUser)
                                    goHomePage.invoke()
                                }
                            }
                        ))
                    }

                    CTextButton(
                        label = stringResource(R.string.signUp)
                    ) {
                        viewModel.handle(AuthContract.Event.SignUpToggle)
                    }
                }

                AnimatedRow(
                    visible = isSignUp
                ) {
                    CButton(
                        label = stringResource(R.string.finish),
                        enabled = userState.value?.isValidUserData() == true
                    ) {
                        viewModel.handle(AuthContract.Event.FinishSignUpClicked(
                            onSuccess = {
                                if (error.isNullOrBlank()) {
                                    mainViewModel.handle(MainContract.Event.LoadUser)
                                    goHomePage.invoke()
                                }
                            }
                        ))

                    }

                    CTextButton(
                        label = stringResource(R.string.back)
                    ) {
                        viewModel.handle(AuthContract.Event.SignUpToggle)
                    }
                }

//                CTextButton(
//                    label = stringResource(R.string.signInHost)
//                ) {
//                    viewModel.handle(AuthContract.Event.SignInHostClicked(
//                        onSuccess = {
//                            if (error.isNullOrBlank()) {
//                                goHomePage.invoke()
//                            }
//                        }
//                    ))
//                }

            }
        }

    }

}


@Composable
private fun AnimatedRow(
    visible: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}