package com.stylianosgakis.rencycompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.stylianosgakis.rencycompose.rates.RateCalculationViewModel
import com.stylianosgakis.rencycompose.style.RencyComposeTheme
import com.stylianosgakis.rencycompose.ui.RatesScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: RateCalculationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RencyComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RatesScreen(viewModel)
                }
            }
        }
    }
}