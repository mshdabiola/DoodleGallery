/*
 *abiola 2024
 */

package com.mshdabiola.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mshdabiola.database.dao.ArtDao
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtDaoTest {
    private lateinit var artDao: ArtDao
    private lateinit var db: DoodleDatabase

    @Before
    fun createDb() {
        val content = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(content, DoodleDatabase::class.java).build()
        artDao = db.getArtDao()
    }

    @Test
    fun upsertTest() = runTest {
    }

    @Test
    fun deleteTest() = runTest {
    }

    @Test
    fun deleteByIdTest() = runTest {
    }
}
