/*
 *abiola 2024
 */

package com.mshdabiola.data.repository.fake

import com.mshdabiola.data.repository.ArtRepository
import com.mshdabiola.model.Art
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeArtRepository @Inject constructor() : ArtRepository {

    private val data = mutableListOf<Art>()
    override suspend fun upsert(art: Art): Long {
        data.add(art)
        val lastIndex = data.lastIndex

        return art.id ?: lastIndex.toLong()
    }

    override fun getAll(): Flow<List<Art>> {
        return flow { data }
    }

    override fun getOne(id: Long): Flow<Art?> {
        return flow { data.find { it.id == id } }
    }

    override suspend fun delete(id: Long) {
        data.removeIf { it.id == id }
    }
}
