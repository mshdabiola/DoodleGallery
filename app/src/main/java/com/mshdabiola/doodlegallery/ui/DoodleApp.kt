/*
 *abiola 2022
 */

package com.mshdabiola.doodlegallery.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.mshdabiola.designsystem.component.DoodleBackground
import com.mshdabiola.designsystem.component.DoodleGradientBackground
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.detail.navigation.MAIN_ROUTE
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.doodlegallery.navigation.DoodleNavHost

@OptIn(
    ExperimentalComposeUiApi::class,
)
@Composable
fun DoodleApp(
    windowSizeClass: WindowSizeClass,
    appState: DoodleAppState = rememberDoodleAppState(
        windowSizeClass = windowSizeClass,
    ),
) {
    val shouldShowGradientBackground = false

    DoodleBackground {
        DoodleGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {
            val snackbarHostState = remember { SnackbarHostState() }


            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                floatingActionButton = {
                    if (appState.currentDestination?.route == MAIN_ROUTE) {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .windowInsetsPadding(WindowInsets.safeDrawing)
                                .testTag("add"),
                            onClick = { appState.navController.navigateToDetail(0) },
                        ) {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = "add note")
//                            Spacer(modifier = )
                            Text(text = "Add note")
                        }
                    }
                },

            ) { padding ->

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
                        ),
                ) {
                    DoodleNavHost(appState = appState)
                }
            }
        }
    }
}
