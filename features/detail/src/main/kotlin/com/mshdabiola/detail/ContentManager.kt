package com.mshdabiola.detail

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.core.content.FileProvider
import com.mshdabiola.model.Coordinate
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ContentManager
@Inject constructor(
    @ApplicationContext val context: Context,
) {

    private val photoDir = context.filesDir.absolutePath + "/photo"
    private val voiceDir = context.filesDir.absolutePath + "/voice"

    fun saveImage(uri: Uri, currentTime: Long) {
        try {
            createImageDir()
            val outputStream = FileOutputStream(File(photoDir, "Image_$currentTime.jpg"))

            context.contentResolver.openInputStream(uri).use {
                it?.copyTo(outputStream)
                outputStream.close()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun saveVoice(uri: Uri, currentTime: Long) {
        createVoiceDir()
        val outputStream = FileOutputStream(File(voiceDir, "Voice_$currentTime.amr"))

        context.contentResolver.openInputStream(uri).use {
            it?.copyTo(outputStream)
            outputStream.close()
        }
    }

    fun pictureUri(id: Long): Uri {
        createImageDir()
        val file = File(photoDir, "Image_$id.jpg")

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

        return uri
    }

    fun getImagePath(data: Long): String {
        return "$photoDir/Image_$data.jpg"
    }

    fun getVoicePath(data: Long): String {
        return "$voiceDir/Voice_$data.amr"
    }

    private fun createVoiceDir() {
        val file = File(voiceDir)
        if (!file.exists()) {
            file.mkdir()
        }
    }

    private fun createImageDir() {
        val file = File(photoDir)
        if (!file.exists()) {
            file.mkdir()
        }
    }

    fun saveBitmap(path: String, bitmap: Bitmap) {
        createImageDir()
        File(path).outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    fun dataFile(drawingId:Long): File {
        val dir = File(context.filesDir.absolutePath + "/drawingfile")
        if (dir.exists().not()) {
            dir.mkdir()
        }

        return File(dir, "data_$drawingId.json")
    }

    private fun saveImage(imageId:Long, paths : List<com.mshdabiola.model.Path>){





            val file = dataFile(imageId)


            val re = context.resources.displayMetrics
            val bitmap = getBitMap(
                paths,
                re.widthPixels,
                re.heightPixels,
                re.density
            )
            val path = getImagePath(imageId)
            saveBitmap(path, bitmap)



            file.delete()
        }

    fun getBitMap(list: List<com.mshdabiola.model.Path>, width: Int, heigth: Int, density: Float): Bitmap {
        val he = heigth - (50 * density)
        val bitmap2 = Bitmap.createBitmap(width, he.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap2.asImageBitmap())

        val paint = Paint()
        canvas.drawRect(
            Rect(0f, 0f, width.toFloat(), he),
            paint.apply { this.color = Color.White },
        )
        list.forEach {
            paint.color = colors[it.property.color]
            paint.alpha = it.property.colorAlpha
            paint.strokeWidth = it.property.lineWidth * density
            // (it.second.lineWidth.dp).roundToPx().toFloat()
            paint.strokeCap = lineCaps[it.property.lineCap]
            paint.strokeJoin = lineJoins[it.property.lineJoin]
            paint.blendMode = DrawScope.DefaultBlendMode
            paint.style = PaintingStyle.Stroke


            canvas.drawPath(toPath(it.paths), paint)
        }

        return bitmap2
    }
    private fun toPath(cord : List<Coordinate>): androidx.compose.ui.graphics.Path {
        var prevOff = Coordinate.Zero
        val yPath = Path()

        val p = cord
            .forEachIndexed { index, offset ->
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

        return yPath
    }

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


}
