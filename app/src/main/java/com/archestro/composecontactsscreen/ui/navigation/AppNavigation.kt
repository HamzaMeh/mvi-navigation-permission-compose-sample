package com.archestro.composecontactsscreen.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.archestro.composecontactsscreen.ui.navigation.destinations.contactscreen.ContactScreenDestination

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination =Navigation.Routes.CONTACT_SCREEN){
        composable(route = Navigation.Routes.CONTACT_SCREEN){
            ContactScreenDestination(navController = navController)
        }
    }
}


object Navigation {
    object Routes {
        const val CONTACT_SCREEN = "contact_screen"
        const val HOME = "home"
    }
}