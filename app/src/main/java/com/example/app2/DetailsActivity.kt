package com.example.app2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.app2.ui.theme.App2Theme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailsActivity : ComponentActivity() {

    private val cardDetailRepository = CardDetailRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val cardId = intent.getStringExtra("CARD_ID")

        setContent {
            App2Theme {

                DetailsScreen(cardId = cardId ?: "")
            }
        }
    }
}

// Function to fetch card details using card ID
suspend fun fetchCardDetails(cardId: String): MagicCardDetails {
    return CardDetailRepository().getCardDetails(cardId)
}

@Composable
fun DetailsScreen(cardId: String) {
    var card by remember { mutableStateOf<MagicCardDetails?>(null) }

    // Fetch card details using card ID
    LaunchedEffect(cardId) {
        val fetchedCard = fetchCardDetails(cardId)
        card = fetchedCard
    }

    // Display card details when available
    if (card != null) {
        DisplayCardDetails(card = card!!)
    } else {
        Text(text = "Loading...")
    }
}



@Composable
fun DisplayCardDetails(card: MagicCardDetails) {
    val card = card.card

    val customColor = Color(235, 129, 164)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            Text(
                text = "Details for ${card.name}",
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            )

            Text(
                text = createAnnotatedStringWithColor(
                    customColor,
                    "Card rarity: ",
                    card.rarity
                ),
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            Text(
                text = createAnnotatedStringWithColor(
                    customColor,
                    "Card subtype: ",
                    card.subtypes?.joinToString(separator = ", ") ?: ""
                ),
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            Text(
                text = createAnnotatedStringWithColor(
                    customColor,
                    "Card type: ",
                    card.types?.joinToString(separator = ", ") ?: ""
                ),
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            Text(
                text = createAnnotatedStringWithColor(
                    customColor,
                    "Card text: ",
                    card.flavor
                ),
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )
            if (card.imageUrl != null) {
                loadImage(card.imageUrl)
            } else {
                loadImage("https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=132106&type=card")
            }
        }
    }
}