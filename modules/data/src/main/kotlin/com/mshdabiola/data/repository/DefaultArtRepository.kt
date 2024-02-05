/*
 *abiola 2024
 */

package com.mshdabiola.data.repository

import com.mshdabiola.common.network.Dispatcher
import com.mshdabiola.common.network.DoodleDispatchers
import com.mshdabiola.data.repository.model.PathDataSer
import com.mshdabiola.data.repository.model.asArt
import com.mshdabiola.data.repository.model.asArtEntity
import com.mshdabiola.data.repository.model.asPath
import com.mshdabiola.data.repository.model.asPathSer
import com.mshdabiola.database.dao.ArtDao
import com.mshdabiola.model.Art
import com.mshdabiola.model.PathData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
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

    override fun getAll(): Flow<List<Art>> {
        return artDao
            .getAll()
            .map { noteEntities -> noteEntities.map { it.asArt(::toList) } }
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

    fun toString(paths: List<PathData>): String {
        return json.encodeToString(
            ListSerializer(PathDataSer.serializer()),
            paths.map { it.asPathSer() },
        )
    }

    fun toList(paths: String): List<PathData> {
        val list: List<PathDataSer> = json.decodeFromString(paths)
        return list.map { it.asPath() }

    }
}
