package com.example.app2


import MainViewModel
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color

@Composable
fun CardList(viewModel: MainViewModel, context: Context) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState(isLoading = true))

    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
        uiState.error != null -> {
            Snackbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = uiState.error!!, color = Color.Red)
            }
        }
        else -> {
            val cards = uiState.data?.filter { !it.imageUrl.isNullOrEmpty() } ?: emptyList()
            LazyColumn {
                items(cards) { card ->
                    CardTile(card = card, context = context)
                }
            }
        }
    }
}
