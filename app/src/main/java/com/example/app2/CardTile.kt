package com.example.app2

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun getUnsafeClient(): OkHttpClient {
    val trustAllCerts: Array<TrustManager> = arrayOf(
        object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    )
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())
    val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    okHttpClientBuilder.hostnameVerifier { _, _ -> true }
    return okHttpClientBuilder.build()
}

@Composable
fun loadImage(url: String) {

    val imageLoader =
        ImageLoader.Builder(LocalContext.current).okHttpClient { getUnsafeClient() }.build()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .height(500.dp)
            .border(
                width = 2.dp,
                color = Color.Black
            ),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.height(500.dp),
            model = url,
            imageLoader = imageLoader,
            placeholder = painterResource(id = R.drawable.placeholder_image),
            error = painterResource(id = R.drawable.placeholder_image),
            contentDescription = "Image"
        )
    }
}

@Composable
fun CardTile(card: MagicCard, context: Context) {
    var isCollapsed by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                isCollapsed = !isCollapsed
            },


    ) {

        Text(
            text = card.name,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
                .height(40.dp),
        )

        if (!isCollapsed) {
            if (card.imageUrl != null) {
                loadImage(card.imageUrl)
            } else {
                loadImage("https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=132106&type=card")
            }

            val customColor = Color(235, 129, 164)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            ) {
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
                        "Card text: \n",
                        card.text
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun createAnnotatedStringWithColor(
    color: Color,
    firstText: String,
    secondText: String
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = color)) {
            append(firstText)
        }
        append(secondText)
    }
}