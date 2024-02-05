package com.mshdabiola.data.repository.model

import com.mshdabiola.model.PathData
import kotlinx.serialization.Serializable

@Serializable
data class PathDataSer(
    val color: Int = 0,
    val lineWidth: Int = 8,
    val lineCap: Int = 0,
    val lineJoin: Int = 0,
    val colorAlpha: Float = 1f,
    val id: Int = 0,
)

fun PathDataSer.asPath()=PathData(color, lineWidth, lineCap, lineJoin, colorAlpha, id)
fun PathData.asPathSer()=PathDataSer(color, lineWidth, lineCap, lineJoin, colorAlpha, id)
