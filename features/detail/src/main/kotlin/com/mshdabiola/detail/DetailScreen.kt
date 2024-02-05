/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mshdabiola.ui.TrackScreenViewEvent

@Composable
internal fun DetailRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    DetailScreen(
        modifier = modifier,
        title = viewModel.noteState.value.title,
        content = viewModel.noteState.value.content,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onDelete = {
            viewModel.onDelete()
            onBack()
        },
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    onBack: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    Column(modifier) {
//        DetailTopAppBar(
//            onNavigationClick = onBack,
//            onDeleteClick = onDelete,
//        )
    }

    TrackScreenViewEvent(screenName = "Detail")
}

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen()
}
