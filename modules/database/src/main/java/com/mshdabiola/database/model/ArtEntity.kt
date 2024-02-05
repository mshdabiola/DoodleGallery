/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "art_table")
data class ArtEntity(
    @PrimaryKey(true)
    val id: Long?,
    val imagePath: String,
    val paths: String,
)

