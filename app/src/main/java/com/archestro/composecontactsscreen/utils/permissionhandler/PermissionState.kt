@file:OptIn(ExperimentalPermissionsApi::class)
package com.archestro.composecontactsscreen.utils.permissionhandler


import com.archestro.composecontactsscreen.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class PermissionState {

    private val _permissionState = MutableStateFlow(State())
    val state : StateFlow<State> = _permissionState

    private val _effect = MutableSharedFlow<Effect>()

    private lateinit var viewModelScope:CoroutineScope
    private lateinit var permissionHandler: PermissionHandler

    fun initPermissionState(viewModelScope: CoroutineScope, permissionHandler: PermissionHandler){
        this.viewModelScope=viewModelScope
        this.permissionHandler=permissionHandler
        permissionHandler
            .state
            .onEach { stateHandler ->
                _permissionState.update { it.copy(multiplePermissionsState = stateHandler.multiplePermissionsState) }
            }
            .catch { Timber.e(it) }
            .launchIn(viewModelScope)
    }

    data class State(
        val multiplePermissionsState: MultiplePermissionsState? = null,
        val permissionAction: PermissionHandler.Action = PermissionHandler.Action.NO_ACTION
    )

    sealed class Event {

        data class Error(val exception: Exception) : Event()

        object PermissionRequired : Event()
    }

    sealed class Effect {

        data class ShowMessage(val message: Int = R.string.something_went_wrong) : Effect()
        data class NavigateTo(val route: String) : Effect()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.Error -> onError()
            Event.PermissionRequired -> onPermissionRequired()
        }
    }

    private fun onError(){
        viewModelScope.launch {
            _effect.emit(Effect.ShowMessage())
        }
    }

    private fun onPermissionRequired(){
        permissionHandler.onEvent(PermissionHandler.Event.PermissionRequired)
    }

}