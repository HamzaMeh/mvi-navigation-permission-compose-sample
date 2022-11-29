@file:OptIn(ExperimentalPermissionsApi::class)

package com.archestro.composecontactsscreen.utils.permissionhandler

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PermissionHandler {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> =_state

    fun onEvent(event:Event){
        when(event) {
            Event.PermissionDenied -> onPermissionDenied()
            Event.PermissionDismissTapped -> onPermissionDismissTapped()
            Event.PermissionNeverAskAgain -> onPermissionNeverAskAgain()
            Event.PermissionRationaleOkTapped -> onPermissionRationaleOkTapped()
            Event.PermissionRequired -> onPermissionRequired()
            Event.PermissionSettingsTapped -> onPermissionSettingsTapped()
            Event.PermissionsGranted -> onPermissionGranted()
            is Event.PermissionsStateUpdated -> onPermissionsStateUpdated(event.permissionsState)
        }
    }

    private fun onPermissionDenied(){
        _state.update { it.copy(permissionAction = Action.NO_ACTION) }
    }

    private fun onPermissionDismissTapped(){
        _state.update { it.copy(permissionAction = Action.NO_ACTION) }
    }

    private fun onPermissionNeverAskAgain(){
        _state.update { it.copy(permissionAction = Action.SHOW_NEVER_ASK_AGAIN) }
    }

    private fun onPermissionRationaleOkTapped(){
        _state.update { it.copy(permissionAction = Action.REQUEST_PERMISSION) }
    }

    private fun onPermissionRequired() {
        _state.value.multiplePermissionsState?.let {
            val permissionAction =
                if (!it.allPermissionsGranted && !it.shouldShowRationale && !it.permissionRequested) {
                    Action.REQUEST_PERMISSION
                } else if (!it.allPermissionsGranted && it.shouldShowRationale) {
                    Action.SHOW_RATIONALE
                } else {
                    Action.SHOW_NEVER_ASK_AGAIN
                }
            _state.update { it.copy(permissionAction = permissionAction) }
        }
    }

    private fun onPermissionSettingsTapped() {
        _state.update { it.copy(permissionAction = Action.NO_ACTION) }
    }

    private fun onPermissionGranted(){
        _state.update { it.copy(permissionAction = Action.NO_ACTION) }
    }

    private fun onPermissionsStateUpdated(permissionState: MultiplePermissionsState) {
        _state.update { it.copy(multiplePermissionsState = permissionState) }
    }


    data class State(
        val multiplePermissionsState: MultiplePermissionsState? = null,
        val permissionAction: Action = Action.NO_ACTION
    )

    enum class Action {
        REQUEST_PERMISSION, SHOW_RATIONALE, SHOW_NEVER_ASK_AGAIN, NO_ACTION
    }

    sealed class Event {
        object PermissionDenied : Event()
        object PermissionsGranted : Event()
        object PermissionSettingsTapped : Event()
        object PermissionNeverAskAgain : Event()
        object PermissionDismissTapped : Event()
        object PermissionRationaleOkTapped : Event()
        object PermissionRequired : Event()

        data class PermissionsStateUpdated(val permissionsState: MultiplePermissionsState) :
            Event()
    }

}