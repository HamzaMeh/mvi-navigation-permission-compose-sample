package com.archestro.composecontactsscreen.di

import com.archestro.composecontactsscreen.ui.features.contacts.ContactsViewModel
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionHandler
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionState
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ContactsViewModel(PermissionState(), PermissionHandler(), androidApplication())
    }

}