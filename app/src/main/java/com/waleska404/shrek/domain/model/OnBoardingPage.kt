package com.waleska404.shrek.domain.model

import androidx.annotation.DrawableRes
import com.waleska404.shrek.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    //TODO: extract string resources
    data object First : OnBoardingPage(
        image = R.drawable.shrek,
        title = "Greetings",
        description = "Welcome to Shrek! If you are a Shrek fan we have great news for you!"
    )

    data object Second : OnBoardingPage(
        image = R.drawable.shrek,
        title = "Explore",
        description = "Find your favorite characters and learn some of the things that you didn't know about them!"
    )

    data object Third : OnBoardingPage(
        image = R.drawable.shrek,
        title = "Power",
        description = "Enjoy the power of Shrek!"
    )
}