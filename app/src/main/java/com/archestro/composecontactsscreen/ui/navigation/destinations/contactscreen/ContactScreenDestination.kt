package com.archestro.composecontactsscreen.ui.navigation.destinations.contactscreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.archestro.composecontactsscreen.ui.features.contacts.ContactsViewModel
import com.archestro.composecontactsscreen.ui.features.contacts.composables.ContactsScreen
import com.archestro.composecontactsscreen.ui.features.contacts.contract.ContactContract
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionState
import org.koin.androidx.compose.getViewModel

@Composable
fun ContactScreenDestination(navController: NavController){
    val viewModel = getViewModel<ContactsViewModel>()
    ContactsScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent ={event -> viewModel.setEvent(event) },
        onNavigationRequested ={navigationEffect ->
            if(navigationEffect is ContactContract.Effect.Navigation.ContactSelected){
               //navController.navigate
            }
        },
        viewModel.permissionState::onEvent,
        viewModel
    )

}