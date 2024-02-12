/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.ArtRepository
import com.mshdabiola.detail.navigation.DetailArgs
import com.mshdabiola.ui.ArtUiState
import com.mshdabiola.ui.asArt
import com.mshdabiola.ui.asUi
import com.mshdabiola.ui.doodle.DrawingController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val artRepository: ArtRepository,
    private val contentManager: ContentManager
) : ViewModel() {
    private val topicArgs: DetailArgs = DetailArgs(savedStateHandle)

    private val topicId = topicArgs.id


    private var _artUiState = mutableStateOf(ArtUiState())
    val artUiState :State<ArtUiState> = _artUiState
    val controller = DrawingController()

    init {
        viewModelScope.launch {
            if (topicId != 0L) {
                val drawPaths = artRepository.getOne(topicId).first()
                drawPaths?.let {
                    _artUiState.value=it.asUi()
                    controller.setPathData(_artUiState.value.paths)

                }
            }else{
                val artUiState=ArtUiState()
                val id =artRepository.upsert(artUiState.asArt().copy(id = null))
                val path=contentManager.getImagePath(id)
                this@DetailViewModel._artUiState.value=artUiState.copy(id = id, imagePath = path)
                controller.setPathData(artUiState.paths)
            }
        }
        viewModelScope.launch(Dispatchers.IO){
//            snapshotFlow {
//                controller.completePathData
//                    .value
//            }
//                .collectLatest {
//                    _artUiState.value=artUiState.value.copy(paths = it)
//                    artRepository.upsert(_artUiState.value.asArt())
//                }
        }
    }

    fun saveData(){
        // Saver.saveGame(imageId = imageID, noteId = noteId)
    }



    fun deleteImage() {
        viewModelScope.launch(Dispatchers.IO) {
            artRepository.delete(_artUiState.value.id)
            File(_artUiState.value.imagePath).deleteOnExit()
        }
    }


}
