/*
 *abiola 2024
 */

package com.mshdabiola.data.repository

import com.mshdabiola.model.Art
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    suspend fun upsert(art: Art): Long
    fun getAll(): Flow<List<Art>>

    fun getOne(id: Long): Flow<Art?>

    suspend fun delete(id: Long)
}
