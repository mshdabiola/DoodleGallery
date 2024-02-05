package com.mshdabiola.model

data class Path(
    val property: PathProperty,
    val paths: List<Coordinate>,
    )
data class PathProperty(
    val id: Int = 0,
    val color: Int = 0,
    val lineWidth: Int = 8,
    val lineCap: Int = 0,
    val lineJoin: Int = 0,
    val colorAlpha: Float = 1f,
)

data class Coordinate(val x : Float,val y : Float) {
    companion object{
        val Zero=Coordinate(0f,0f)
    }
}
enum class MODE {
    UP,
    MOVE,
    DOWN,
}

enum class DRAW_MODE {
    ERASE,
    PEN,
    MARKER,
    CRAYON,
}
