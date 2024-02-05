package com.mshdabiola.data.repository.model

import com.mshdabiola.model.Coordinate
import com.mshdabiola.model.PathProperty
import com.mshdabiola.model.Path
import kotlinx.serialization.Serializable
@Serializable
data class PathsSer(
    val property: PathPropertySer,
    val paths: List<CoordinateSer>,
)
@Serializable
data class PathPropertySer(
    val id: Int = 0,
    val color: Int = 0,
    val lineWidth: Int = 8,
    val lineCap: Int = 0,
    val lineJoin: Int = 0,
    val colorAlpha: Float = 1f,
)

@Serializable
data class CoordinateSer(val x : Float,val y : Float)
fun PathsSer.asPath()=Path(property.asPathProperty(),paths.map { it.asCoordinate() })
fun Path.asPathSer()=PathsSer(property.asPathPropertySer(),paths.map { it.asCoordinateSer() })

fun CoordinateSer.asCoordinate()= Coordinate(x, y)
fun Coordinate.asCoordinateSer()= CoordinateSer(x, y)

fun PathPropertySer.asPathProperty()= PathProperty(
    id = id,
    color = color,
    lineWidth = lineWidth,
    lineCap = lineCap,
    lineJoin = lineJoin,
    colorAlpha = colorAlpha
)

fun PathProperty.asPathPropertySer()= PathPropertySer(
    id = id,
    color = color,
    lineWidth = lineWidth,
    lineCap = lineCap,
    lineJoin = lineJoin,
    colorAlpha = colorAlpha
)