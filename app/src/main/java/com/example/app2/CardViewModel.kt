package com.example.app2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CardViewModel : ViewModel() {
    private val uiState = MutableLiveData<UiState<List<MagicCard>>>()

    fun getUiState(): LiveData<UiState<List<MagicCard>>> {
        return uiState
    }

    fun setData(data: List<MagicCard>) {
        uiState.value = UiState(data = data, isLoading = false, error = null)
    }

    fun setLoading() {
        uiState.value = UiState(data = null, isLoading = true, error = null)
    }

    fun setError(errorMessage: String) {
        uiState.value = UiState(data = null, isLoading = false, error = errorMessage)
    }
}
