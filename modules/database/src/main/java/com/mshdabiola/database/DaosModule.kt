/*
 *abiola 2024
 */

package com.mshdabiola.database

import com.mshdabiola.database.dao.ArtDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun noteDaoProvider(db: DoodleDatabase): ArtDao {
        return db.getArtDao()
    }
}
