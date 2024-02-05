/*
 *abiola 2024
 */

package com.mshdabiola.data.repository

import com.mshdabiola.common.network.Dispatcher
import com.mshdabiola.common.network.DoodleDispatchers
import com.mshdabiola.data.repository.model.PathsSer
import com.mshdabiola.data.repository.model.asArt
import com.mshdabiola.data.repository.model.asArtEntity
import com.mshdabiola.data.repository.model.asPath
import com.mshdabiola.data.repository.model.asPathSer
import com.mshdabiola.database.dao.ArtDao
import com.mshdabiola.model.Art
import com.mshdabiola.model.ArtMin
import com.mshdabiola.model.Path
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultArtRepository @Inject constructor(
    private val artDao: ArtDao,
    @Dispatcher(DoodleDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ArtRepository {

    private val json = Json
    override suspend fun upsert(art: Art): Long {
        return withContext(ioDispatcher) {
            artDao.upsert(art.asArtEntity(::toString))
        }
    }

    override fun getAll(): Flow<List<ArtMin>> {
        return artDao
            .getAll()
            .map { noteEntities -> noteEntities.map { ArtMin(it.id!!,it.imagePath)} }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Art?> {
        return artDao
            .getOne(id)
            .map { it?.asArt(::toList) }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            artDao.delete(id)
        }
    }

    private fun toString(paths: List<Path>): String {
        return json.encodeToString(paths.map { it.asPathSer() })
    }

    private fun toList(paths: String): List<Path> {
      return json.decodeFromString<List<PathsSer>>(paths).map { it.asPath() }
    }
}
