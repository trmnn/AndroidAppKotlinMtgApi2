package com.example.app2

data class MagicCardsResponse(
    val cards: List<MagicCard>
)

data class MagicCard(
    val id: String,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val subtypes: List<String>,
    val rarity: String,
    val text: String
)
data class MagicCardDetails(
    val card: CardDetails
)

data class CardDetails(
    val name: String,
    val manaCost: String,
    val cmc: Double,
    val colors: List<String>,
    val colorIdentity: List<String>,
    val type: String,
    val types: List<String>,
    val subtypes: List<String>,
    val rarity: String,
    val set: String,
    val setName: String,
    val text: String,
    val flavor: String,
    val artist: String,
    val number: String,
    val power: String?,
    val toughness: String,
    val layout: String,
    val multiverseid: String,
    val imageUrl: String,
    val variations: List<String>,
    val foreignNames: List<ForeignName>,
    val printings: List<String>,
    val originalText: String,
    val originalType: String,
    val legalities: List<Legality>
)

data class ForeignName(
    val name: String?,
    val text: String?,
    val type: String?,
    val flavor: String?,
    val imageUrl: String?,
    val language: String?,
    val multiverseid: Int?
)

data class Legality(
    val format: String?,
    val legality: String?
)
