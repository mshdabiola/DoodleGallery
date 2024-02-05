/*
 *abiola 2024
 */

package com.mshdabiola.data.di

import com.mshdabiola.data.repository.DefaultArtRepository
import com.mshdabiola.data.repository.ArtRepository
import com.mshdabiola.data.repository.OfflineFirstUserDataRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.data.util.ConnectivityManagerNetworkMonitor
import com.mshdabiola.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindModelRepository(realModelRepository: DefaultArtRepository): ArtRepository

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository
}
