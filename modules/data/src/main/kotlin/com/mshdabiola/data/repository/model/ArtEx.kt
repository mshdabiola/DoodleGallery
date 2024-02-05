/*
 *abiola 2024
 */

package com.mshdabiola.data.repository.model

import com.mshdabiola.database.model.ArtEntity
import com.mshdabiola.model.Art
import com.mshdabiola.model.Path

fun Art.asArtEntity(toString: (List<Path>)->String) = ArtEntity(id, imagePath, toString(paths))

fun ArtEntity.asArt(toList: (String)->List<Path>)=Art(id, imagePath, toList(paths))


