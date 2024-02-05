/*
 *abiola 2024
 */

package com.mshdabiola.model

data class Art(
    val id: Long? = null,
    val imagePath: String = "",
    val paths:List<Path>,
)

data class ArtMin(
    val id: Long,
    val imagePath: String
)
