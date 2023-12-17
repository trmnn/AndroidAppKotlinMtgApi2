// com.example.app2.MainViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app2.CardRepository
import com.example.app2.CardViewModel
import com.example.app2.MagicCard
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.app2.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val cardRepository: CardRepository) : ViewModel() {
    private val _uiState = MutableLiveData<UiState<List<MagicCard>>>()
    val uiState: LiveData<UiState<List<MagicCard>>> = _uiState

    private var retryCount = 0

    fun fetchMagicCards() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState(isLoading = true)
                val allCards = cardRepository.getMagicCards()
                val sortedCards = allCards.sortedBy { it.name }
                _uiState.value = UiState(data = sortedCards)
                retryCount = 0
            } catch (e: Exception) {
                if (retryCount < 3) {
                    retryCount++
                    _uiState.value = UiState(error = "Retry attempt $retryCount...")
                    delay(1000L)
                    fetchMagicCards()
                } else {
                    _uiState.value = UiState(error = "Failed after 3 retries: ${e.localizedMessage}")
                }
            }
        }
    }
}
