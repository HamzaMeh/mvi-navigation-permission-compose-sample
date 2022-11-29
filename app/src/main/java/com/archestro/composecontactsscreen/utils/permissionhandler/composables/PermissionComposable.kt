@file:OptIn(ExperimentalPermissionsApi::class)

package com.archestro.composecontactsscreen.utils.permissionhandler.composables

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.archestro.composecontactsscreen.R
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@Composable
fun HandlePermissionsRequest(permissions: List<String>, permissionsHandler: PermissionHandler) {

    val state by permissionsHandler.state.collectAsState()
    val permissionsState = rememberMultiplePermissionsState(permissions)

    LaunchedEffect(permissionsState) {
        permissionsHandler.onEvent(PermissionHandler.Event.PermissionsStateUpdated(permissionsState))
        when {
            permissionsState.allPermissionsGranted -> {
                permissionsHandler.onEvent(PermissionHandler.Event.PermissionsGranted)
            }
            permissionsState.permissionRequested && !permissionsState.shouldShowRationale -> {
                permissionsHandler.onEvent(PermissionHandler.Event.PermissionNeverAskAgain)
            }
            else -> {
                permissionsHandler.onEvent(PermissionHandler.Event.PermissionDenied)
            }
        }
    }

    HandlePermissionAction(
        action = state.permissionAction,
        permissionStates = state.multiplePermissionsState,
        rationaleText = R.string.permission_rationale,
        neverAskAgainText = R.string.permission_rationale,
        onOkTapped = { permissionsHandler.onEvent(PermissionHandler.Event.PermissionRationaleOkTapped) },
        onSettingsTapped = { permissionsHandler.onEvent(PermissionHandler.Event.PermissionSettingsTapped) },
    )
}

@Composable
fun HandlePermissionAction(
    action: PermissionHandler.Action,
    permissionStates: MultiplePermissionsState?,
    @StringRes rationaleText: Int,
    @StringRes neverAskAgainText: Int,
    onOkTapped: () -> Unit,
    onSettingsTapped: () -> Unit,
) {
    val context = LocalContext.current
    when (action) {
        PermissionHandler.Action.REQUEST_PERMISSION -> {
            LaunchedEffect(true) {
                permissionStates?.launchMultiplePermissionRequest()
            }
        }
        PermissionHandler.Action.SHOW_RATIONALE -> {
            PermissionRationaleDialog(
                message = stringResource(rationaleText),
                onOkTapped = onOkTapped
            )
        }
        PermissionHandler.Action.SHOW_NEVER_ASK_AGAIN -> {
            ShowGotoSettingsDialog(
                title = stringResource(R.string.allow_permission),
                message = stringResource(neverAskAgainText),
                onSettingsTapped = {
                    onSettingsTapped()
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:" + context.packageName)
                        context.startActivity(this)
                    }
                },
            )
        }
        PermissionHandler.Action.NO_ACTION -> Unit
    }
}


@Composable
fun PermissionRationaleDialog(
    message: String,
    title: String = stringResource(R.string.allow_permission),
    primaryButtonText: String = stringResource(R.string.ok),
    onOkTapped: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
        },
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = primaryButtonText.uppercase(),
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .clickable { onOkTapped() },
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}


@Composable
fun ShowGotoSettingsDialog(
    title: String,
    message: String,
    onSettingsTapped: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        },
        buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = stringResource(id = R.string.settings),
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .clickable { onSettingsTapped() },
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}
