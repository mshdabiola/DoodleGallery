/*
 *abiola 2024
 */

package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.database.model.ArtEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtDao {

    @Upsert
    suspend fun upsert(artEntity: ArtEntity): Long

    @Query("SELECT * FROM art_table")
    fun getAll(): Flow<List<ArtEntity>>

    @Query("SELECT * FROM art_table WHERE id = :id")
    fun getOne(id: Long): Flow<ArtEntity?>

    @Query("DELETE FROM art_table WHERE id = :id")
    suspend fun delete(id: Long)
}
