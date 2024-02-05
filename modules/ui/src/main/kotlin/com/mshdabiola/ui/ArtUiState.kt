package com.mshdabiola.ui

import com.mshdabiola.model.Art
import com.mshdabiola.ui.doodle.PathsUiState
import com.mshdabiola.ui.doodle.asPath
import com.mshdabiola.ui.doodle.asUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ArtUiState(
    val id: Long=0,
    val imagePath: String="",
    val paths: ImmutableList<PathsUiState> = emptyList<PathsUiState>().toImmutableList(),
)

fun Art.asUi()=ArtUiState(id ?:0,imagePath,paths.map { it.asUi() }.toImmutableList())

fun ArtUiState.asArt()=Art(id,imagePath,paths.map { it.asPath() })
