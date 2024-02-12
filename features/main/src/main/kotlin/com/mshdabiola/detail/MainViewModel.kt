/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.ArtRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.ui.MainState
import com.mshdabiola.ui.asUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val artRepository: ArtRepository,
) : ViewModel() {


    val feedUiMainState: StateFlow<MainState> =
        artRepository.getAll()
            .map { notes -> MainState.Success(notes.map { it.asUi() }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainState.Loading,
            )
}
