/*
 *abiola 2024
 */

package com.mshdabiola.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mshdabiola.database.dao.ArtDao
import com.mshdabiola.database.model.ArtEntity

@Database(
    entities = [ArtEntity::class],
    version = 1,
//    autoMigrations = [
//        //AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
//
//                     ]
//    ,
    exportSchema = true,
)
@TypeConverters()
abstract class DoodleDatabase : RoomDatabase() {

    abstract fun getArtDao(): ArtDao
//
//    abstract fun getPlayerDao(): PlayerDao
//
//    abstract fun getPawnDao(): PawnDao
}
