package com.mshdabiola.ui.doodle

import android.annotation.SuppressLint
import android.graphics.RectF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import com.mshdabiola.model.DRAW_MODE
import com.mshdabiola.model.MODE
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@SuppressLint("MutableCollectionMutableState")
class DrawingController {
    val colors = arrayOf(
        Color.Black,
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Magenta,
        Color.Cyan,
        Color.Yellow,
        Color(0xFF651FFF),
        Color(0xFFD500F9),
        Color(0xFFFFEA00),
        Color(0xFF1DE9B6),
        Color(0xFFF50057),
        Color(0xFFFF3D00),

        )

    val lineCaps = arrayOf(StrokeCap.Round, StrokeCap.Butt, StrokeCap.Round)
    val lineJoins = arrayOf(StrokeJoin.Round, StrokeJoin.Bevel, StrokeJoin.Miter)

    var lineWidth = 8
    var lineCap = 0
    private var lineJoin = 0
    var color = 1
    var draw_mode = DRAW_MODE.PEN
    var colorAlpha = 1f

    private var _unCompletePathData = mutableStateOf(emptyList<PathsUiState>().toImmutableList())
    val unCompletePathData: State<ImmutableList<PathsUiState>> = _unCompletePathData

    private var _completePathData = mutableStateOf(emptyList<PathsUiState>().toImmutableList())
    val completePathData: State<ImmutableList<PathsUiState>> = _completePathData

    private val redoPaths = mutableListOf<PathsUiState>()
    private val _canUndo = mutableStateOf(false)
    val canUndo: State<Boolean> = _canUndo

    private val _canRedo = mutableStateOf(false)
    val canRedo: State<Boolean> = _canRedo

    fun getColor(index: Int) = colors[index]

    private var xx = 0f
    private var yy = 0f
    var property = PropertyUiState()
    fun setPathData(x: Float, y: Float, mode: MODE) {
        when (draw_mode) {
            DRAW_MODE.ERASE -> {
                when(mode){
                    MODE.DOWN->{
                        xx = x
                        yy = y
                    }
                    MODE.MOVE->{
                        val rect = RectF(minOf(xx, x), minOf(y, yy), maxOf(xx, x), maxOf(y, yy))
                        val paths = _unCompletePathData.value.toMutableList()
                        val path =
                            paths.filter { entry -> entry.paths.any { rect.contains(it.x, it.y) } }
                        if (path.isNotEmpty()){
                            path.forEach { p ->
                                paths.remove(p)
                                redoPaths.add(p)
                            }
                            //rearrange id
                            val newPaths= mutableListOf<PathsUiState>()
                           paths
                                .toList()
                                .forEachIndexed { index, pair ->
                                    val newPath=pair.copy(property = pair.property.copy(id = index))
                                    newPaths.add(newPath)
                                }


                                _unCompletePathData.value=newPaths.toImmutableList()
                        }

                    }
                    MODE.UP->{
                        setCompleteList()
                    }
                }
            }

            else -> {
                when (mode) {
                    MODE.DOWN -> {
                        val id = _unCompletePathData.value.size
                        property = PropertyUiState(
                            id = id,
                            color = color,
                            lineWidth = lineWidth,
                            lineCap = lineCap,
                            lineJoin = lineJoin,
                            colorAlpha = colorAlpha,
                        )
                        //  id++
                        val paths2 = _unCompletePathData.value.toMutableList()
                        val list = emptyList<Offset>().toMutableList()

                        list.add(Offset(x, y))
                        paths2.add(PathsUiState(property, list))
                       _unCompletePathData.value= paths2.toImmutableList()
                    }

                    MODE.MOVE -> {
                        val paths2 = _unCompletePathData.value.toMutableList()
                        val path=paths2[property.id]
                        val list = path.paths .toMutableList()

                        list.add(Offset(x, y))
                        paths2[property.id] = path.copy(paths = list)
                       _unCompletePathData.value = paths2.toImmutableList()
                    }

                    MODE.UP -> {
                        //save data
                        setCompleteList()
                    }
                }
            }
        }
        setDoUnDo()
    }

    fun setPathData(pathDatas: List<PathsUiState>) {
        val paths = _unCompletePathData.value.toMutableList()
        paths.addAll(pathDatas)
        //  id = pathDatas.size
        _unCompletePathData.value= paths.toImmutableList()
        _completePathData.value=paths.toImmutableList()
    }

    fun undo() {
        if (canUndo.value) {
            val paths = _unCompletePathData.value.toMutableList()
            redoPaths.add( paths.removeLast())
            _unCompletePathData.value= paths.toImmutableList()
            setDoUnDo()
        }
    }

    private fun setDoUnDo() {
        _canUndo.value = _unCompletePathData.value.isNotEmpty()
        _canRedo.value = redoPaths.isNotEmpty()

        //save data
        setCompleteList()
    }

    fun redo() {
        if (canRedo.value) {
            val paths = _unCompletePathData.value.toMutableList()
            paths.add(redoPaths.removeLast())
            _unCompletePathData.value= paths.toImmutableList()

            setDoUnDo()
            // listOfPathData.value.add(redoPaths.removeLast())
        }
    }

    fun getPathAndProperty(): List<Pair<Path, PropertyUiState>> {
        var prevOff = Offset.Zero

        val p = _unCompletePathData
            .value
            .map {
                val yPath = Path()
                it.paths.forEachIndexed { index, offset ->
                    prevOff = if (index == 0) {
                        yPath.moveTo(offset.x, offset.y)
                        offset
                    } else {
                        yPath.quadraticBezierTo(
                            prevOff.x,
                            prevOff.y,
                            (prevOff.x + offset.x) / 2,
                            (prevOff.y + offset.y) / 2,
                        )
                        offset
                    }
                }
                Pair(yPath, it.property)
            }
        return p
    }

    fun clearPath() {
        val paths = _unCompletePathData.value.toMutableList()
        paths.clear()
        redoPaths.clear()
        _unCompletePathData.value = paths.toImmutableList()
        setDoUnDo()
        //save data
    }

    private fun setCompleteList(){
        _completePathData.value=unCompletePathData.value

    }

}

@Composable
fun rememberDrawingController(): DrawingController {
    return remember {
        DrawingController()
    }
}
