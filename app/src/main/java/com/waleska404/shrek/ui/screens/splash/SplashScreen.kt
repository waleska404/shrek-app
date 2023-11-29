package com.waleska404.shrek.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.waleska404.shrek.R
import com.waleska404.shrek.ui.theme.ShrekTheme
import com.waleska404.shrek.util.LightAndDarkPreviews

@Composable
fun SplashScreen(navController: NavHostController) {
    val rotate = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        rotate.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )
    }

    Splash(rotate.value)
}


@Composable
fun Splash(rotate: Float) {
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.rotate(degrees = rotate),
            painter = painterResource(id = R.drawable.shrek),
            contentDescription = stringResource(R.string.app_logo),
        )
    }
}

@Composable
@LightAndDarkPreviews
fun SplashScreenPreview() {
    ShrekTheme {
        Splash(0f)
    }
}