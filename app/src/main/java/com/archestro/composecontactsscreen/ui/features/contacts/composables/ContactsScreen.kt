package com.archestro.composecontactsscreen.ui.features.contacts.composables

import android.Manifest
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.archestro.composecontactsscreen.base.SIDE_EFFECTS_KEY
import com.archestro.composecontactsscreen.data.local.contact.ContactModel
import com.archestro.composecontactsscreen.ui.common.progress.Progress
import com.archestro.composecontactsscreen.ui.common.topbar.TopBar
import com.archestro.composecontactsscreen.ui.features.contacts.ContactsViewModel
import com.archestro.composecontactsscreen.ui.features.contacts.contract.ContactContract
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionHandler
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionState
import com.archestro.composecontactsscreen.utils.permissionhandler.composables.HandlePermissionsRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContactsScreen(
    state: ContactContract.State,
    effectFlow : Flow<ContactContract.Effect>,
    onEventSent :(event : ContactContract.Event) ->Unit,
    onNavigationRequested: (navigationEffect: ContactContract.Effect.Navigation) -> Unit,
    permissionEvent:(PermissionState.Event)->Unit,
    viewModel: ContactsViewModel
){
    val permissionState by viewModel.permissionState.state.collectAsState()
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val permissions = remember { listOf(Manifest.permission.READ_CONTACTS) }
    HandlePermissionsRequest(permissions = permissions, permissionsHandler =viewModel.permissionHandler)

    if(permissionState.multiplePermissionsState?.allPermissionsGranted == true && !state.isCompleted){
        viewModel.fetchContacts()
    }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow.onEach { effect ->
            when (effect) {
                is ContactContract.Effect.DataWasLoaded -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message =" snackBarMessage",
                        duration = SnackbarDuration.Short
                    )
                }
                is ContactContract.Effect.Navigation.ContactSelected -> onNavigationRequested(effect)
            }
        }.collect()
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {  TopBar(text = "Select or Enter Mobile No.") {} }
    ) { padding ->
        when{
            state.isLoading -> Progress()
            state.isError -> {}
            else -> ContactsList(
                isContactsPermissionGranted = permissionState.multiplePermissionsState?.allPermissionsGranted?: false,
                contacts = state.contacts, padding = padding, permissionEvent){
                onEventSent(ContactContract.Event.ContactSelected(it))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ContactScreenPreview() {
    val contact = ContactModel("1", "Username", "0423456789")
    val list = List(10) { contact }
    Scaffold(
        topBar = {  TopBar(text = "Select or Enter Mobile No.") {} }
    ) { padding ->
        ContactsList(
                isContactsPermissionGranted = false,
                contacts = list,
            padding = padding,
            {}){
            {}
        }
    }
}






