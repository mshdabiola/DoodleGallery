package com.mshdabiola.ui.doodle

import androidx.compose.ui.geometry.Offset
import com.mshdabiola.model.Coordinate
import com.mshdabiola.model.PathProperty
import com.mshdabiola.model.Path

data class PathsUiState(
    val property: PropertyUiState,
    val paths: List<Offset>,
)

data class PropertyUiState(
    val id: Int = 0,
    val color: Int = 0,
    val lineWidth: Int = 8,
    val lineCap: Int = 0,
    val lineJoin: Int = 0,
    val colorAlpha: Float = 1f,
)

fun Path.asUi()=PathsUiState(property.asUi(),paths.map { Offset(it.x,it.y) })

fun PathsUiState.asPath()=Path(property.asProperty(),paths.map { Coordinate(it.x,it.y) })


internal fun PathProperty.asUi()=PropertyUiState(id, color, lineWidth, lineCap, lineJoin, colorAlpha)

internal fun PropertyUiState.asProperty()=PathProperty(id, color, lineWidth, lineCap, lineJoin, colorAlpha)