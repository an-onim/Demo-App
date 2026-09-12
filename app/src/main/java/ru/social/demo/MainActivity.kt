package ru.social.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.social.demo.data.SharedPrefs
import ru.social.demo.ui.components.BottomBar
import ru.social.demo.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val needAuth = SharedPrefs(context = applicationContext).getUserId().isNullOrBlank()

        enableEdgeToEdge()
        setContent {
            AppTheme {
                BottomBar(needAuth)
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }

}
