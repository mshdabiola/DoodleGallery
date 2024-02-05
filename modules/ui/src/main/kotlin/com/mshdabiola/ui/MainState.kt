package com.mshdabiola.ui

import com.mshdabiola.model.ArtMin

sealed interface MainState {
    data object Loading : MainState
    data class Success(
        val arts: List<ArtMinUiState>,
    ) : MainState
}

data class ArtMinUiState(
    val  id :Long,
    val imagePath:String)

fun ArtMin.asUi()=ArtMinUiState(id, imagePath)
