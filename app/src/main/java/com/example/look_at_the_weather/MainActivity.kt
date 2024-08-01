package com.example.look_at_the_weather

import SplashScreen
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.look_at_the_weather.composables.WeatherPage
import com.example.look_at_the_weather.ui.theme.LookattheweatherTheme
import com.example.look_at_the_weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.lang.reflect.Modifier

/**
 * Once Hilt is set up in your Application class and an application-level component is available,
 * Hilt can provide dependencies to other Android classes that have the @AndroidEntryPoint annotation:
 * */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LookattheweatherTheme {
                AppContent(viewModel)
            }
        }
    }
}

@Composable
fun AppContent(viewModel: WeatherViewModel) {
    var showSplash = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000) // 3 seconds delay
        showSplash.value = false
    }

    if (showSplash.value) {
        SplashScreen()
    } else {
        WeatherPage(viewModel) // Pass the ViewModel
    }
}
