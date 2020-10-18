package com.stylianosgakis.rencycompose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import coil.ImageLoader
import coil.request.ImageRequest
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun SimpleCoilImage(
    modifier: Modifier = Modifier,
    url: String,
) {
    CoilImage(
        modifier = modifier,
        imageRequest = ImageRequest.Builder(ContextAmbient.current)
            .data(url)
            .crossfade(true)
            .build(),
        imageLoader = ImageLoader.Builder(ContextAmbient.current)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.FillHeight,
        alignment = Alignment.Center,
    )
}