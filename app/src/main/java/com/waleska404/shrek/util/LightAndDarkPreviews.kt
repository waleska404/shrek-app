package com.waleska404.shrek.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "LightMode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = false,
    group = "LightMode"
)
@Preview(
    name = "DarkMode",
    uiMode = Configuration.UI_MODE_NIGHT_MASK and Configuration.UI_MODE_NIGHT_YES,
    showBackground = false,
    group = "DarkMode"
)
annotation class LightAndDarkPreviews