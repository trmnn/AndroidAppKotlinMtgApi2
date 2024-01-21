package com.example.app2

import MainViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.app2.ui.theme.App2Theme


class MainActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchMagicCards()

        setContent {
            App2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CardList(
                        viewModel = viewModel,
                        onClick = { cardId -> navigateToDetails(cardId) }
                    )
                }
            }
        }
    }

    private fun navigateToDetails(cardId: String) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("CARD_ID", cardId)
        }
        startActivity(intent)
    }
}
