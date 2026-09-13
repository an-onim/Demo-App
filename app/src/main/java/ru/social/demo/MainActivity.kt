package ru.social.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.social.core.base.store.SharedPrefs
import ru.social.demo.ui.BottomBar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val needAuth = SharedPrefs(context = applicationContext).getUserId().isNullOrBlank()

        enableEdgeToEdge()
        setContent {
            _root_ide_package_.ru.social.core.ui.common.theme.AppTheme {
                BottomBar(needAuth)
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }

}
