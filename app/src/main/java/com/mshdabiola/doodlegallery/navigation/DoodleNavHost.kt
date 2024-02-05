/*
 *abiola 2022
 */

package com.mshdabiola.doodlegallery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mshdabiola.detail.navigation.MAIN_ROUTE
import com.mshdabiola.detail.navigation.detailScreen
import com.mshdabiola.detail.navigation.mainScreen
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.doodlegallery.ui.DoodleAppState

@Composable
fun DoodleNavHost(
    appState: DoodleAppState,
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        mainScreen(onClicked = navController::navigateToDetail)
        detailScreen(navController::popBackStack)
    }
}
