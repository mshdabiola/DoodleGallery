/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.designsystem.component.DoodleTopAppBar
import com.mshdabiola.designsystem.component.SkLoadingWheel
import com.mshdabiola.designsystem.theme.DoodleTheme
import com.mshdabiola.designsystem.theme.LocalTintTheme
import com.mshdabiola.main.R
import com.mshdabiola.ui.MainState
import com.mshdabiola.ui.MainState.Loading
import com.mshdabiola.ui.MainState.Success
import com.mshdabiola.ui.NoteUiState
import com.mshdabiola.ui.TrackScreenViewEvent

@Composable
internal fun MainRoute(
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val feedState by viewModel.feedUiMainState.collectAsStateWithLifecycle()
    MainScreen(
        mainState = feedState,
        modifier = modifier,
        onClick = onClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun MainScreen(
    mainState: MainState,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (mainState) {
        Loading -> LoadingState(modifier)
        is Success ->
            {
                Scaffold(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    topBar = {
                        DoodleTopAppBar(titleRes = com.mshdabiola.designsystem.R.string.modules_designsystem_app_name)
                    },
                ) {
                    if (mainState.noteUiStates.isNotEmpty()) {
                        MainContent(
                            mainState,
                            onClick,
                            modifier.padding(it),
                        )
                    } else {
                        EmptyState(modifier.padding(it))
                    }
                }
            }
    }

    TrackScreenViewEvent(screenName = "Main")
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        SkLoadingWheel(
            modifier = modifier
                .align(Alignment.Center)
                .testTag("main:loading"),
            contentDesc = stringResource(id = R.string.features_main_loading),
        )
    }
}

@Composable
private fun MainContent(
    state: MainState,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(Modifier.fillMaxSize()) {
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("main:empty"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val iconTint = LocalTintTheme.current.iconTint
        Image(
            modifier = Modifier.size(180.dp),
            painter = painterResource(id = com.mshdabiola.designsystem.R.drawable.modules_designsystem_img_empty_bookmarks),
            colorFilter = if (iconTint != Color.Unspecified) ColorFilter.tint(iconTint) else null,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(id = R.string.features_main_empty_error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.features_main_empty_description),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun LoadingStatePreview() {
    DoodleTheme {
        LoadingState()
    }
}

@Preview
@Composable
private fun MainListPreview() {
    DoodleTheme {
        MainContent(
            state = Success(
                listOf(
                    NoteUiState(
                        id = 5257L,
                        title = "Jacinto",
                        description = "Charisma",
                    ),
                    NoteUiState(id = 7450L, title = "Dewayne", description = "Justan"),
                    NoteUiState(id = 1352L, title = "Bjorn", description = "Daquan"),
                    NoteUiState(id = 4476L, title = "Tonya", description = "Ivelisse"),
                    NoteUiState(id = 6520L, title = "Raegan", description = "Katrena"),
                    NoteUiState(id = 5136L, title = "Markis", description = "Giles"),
                    NoteUiState(id = 6868L, title = "Virgilio", description = "Ashford"),
                    NoteUiState(id = 7100L, title = "Larae", description = "Krystyn"),
                    NoteUiState(id = 3210L, title = "Nigel", description = "Sergio"),
                    NoteUiState(id = 7830L, title = "Kristy", description = "Jacobi"),
                    NoteUiState(id = 1020L, title = "Kathlene", description = "Shlomo"),
                    NoteUiState(id = 3365L, title = "Corin", description = "Ross"),

                ),
            ),

            onClick = {},
        )
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    DoodleTheme {
        EmptyState()
    }
}
