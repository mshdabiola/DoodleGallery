/*
 *abiola 2022
 */

package com.mshdabiola.data.repository.fake

import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.datastore.DoodlePreferencesDataSource
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Fake implementation of the [UserDataRepository] that returns hardcoded user data.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeUserDataRepository @Inject constructor(
    private val doodlePreferencesDataSource: DoodlePreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        doodlePreferencesDataSource.userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        doodlePreferencesDataSource.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        doodlePreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        doodlePreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        doodlePreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
    }
}
