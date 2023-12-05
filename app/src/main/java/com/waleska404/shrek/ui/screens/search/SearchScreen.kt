package com.waleska404.shrek.ui.screens.search

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.waleska404.shrek.ui.common.ListContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    val searchQuery by searchViewModel.searchQuery
    val characters = searchViewModel.searchedCharacters.collectAsLazyPagingItems()
    val systemBarColor = MaterialTheme.colors.primary.toArgb()

    SideEffect { activity.window.statusBarColor = systemBarColor }

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                },
                onSearchClicked = {
                    searchViewModel.searchCharacters(query = it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = {
            ListContent(characters = characters, navController = navController)
        }
    )
}