package com.waleska404.shrek.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.wear.compose.material.ContentAlpha
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.waleska404.shrek.R
import com.waleska404.shrek.domain.model.ShrekCharacter
import com.waleska404.shrek.navigation.Screen
import com.waleska404.shrek.ui.components.RatingWidget
import com.waleska404.shrek.ui.components.ShimmerEffect
import com.waleska404.shrek.ui.theme.CHARACTER_ITEM_HEIGHT
import com.waleska404.shrek.ui.theme.LARGE_PADDING
import com.waleska404.shrek.ui.theme.MEDIUM_PADDING
import com.waleska404.shrek.ui.theme.SMALL_PADDING
import com.waleska404.shrek.util.Constants.BASE_URL

@ExperimentalCoilApi
@Composable
fun ListContent(
    characters: LazyPagingItems<ShrekCharacter>,
    navController: NavHostController
) {
    val result = handlePagingResult(characters = characters)

    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = characters.itemSnapshotList.items,
                key = { character ->
                    character.id
                }
            ) { character ->
                CharacterItem(character = character, navController = navController)
            }
        }
    }
}

@Composable
fun handlePagingResult(
    characters: LazyPagingItems<ShrekCharacter>
): Boolean {
    characters.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error, characters = characters)
                false
            }
            characters.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CharacterItem(
    character: ShrekCharacter,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .height(CHARACTER_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.Details.passCharacterId(id = character.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = "$BASE_URL${character.image}")
                    .placeholder(drawableResId = R.drawable.ic_placeholder)
                    .error(drawableResId = R.drawable.ic_placeholder)
                    .build(),
                contentDescription = stringResource(id = R.string.character_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = character.name,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = character.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = character.rating
                    )
                    Text(
                        text = "(${character.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
@Preview
fun CharacterItemPreview() {
    CharacterItem(
        character = ShrekCharacter(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            rating = 0.0,
            power = 100,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        navController = rememberNavController()
    )
}

@ExperimentalCoilApi
@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun CharacterItemDarkPreview() {
    CharacterItem(
        character = ShrekCharacter(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            rating = 0.0,
            power = 100,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        navController = rememberNavController()
    )
}