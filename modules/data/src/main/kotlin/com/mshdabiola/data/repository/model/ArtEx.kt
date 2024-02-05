/*
 *abiola 2024
 */

package com.mshdabiola.data.repository.model

import com.mshdabiola.database.model.ArtEntity
import com.mshdabiola.model.Art
import com.mshdabiola.model.PathData

fun Art.asArtEntity(toString: (List<PathData>)->String) = ArtEntity(id, imagePath, toString(drawPaths))

fun ArtEntity.asArt(toList: (String)->List<PathData>)=Art(id, imagePath, toList(drawPaths))


