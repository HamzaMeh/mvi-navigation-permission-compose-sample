@file:OptIn(ExperimentalMaterial3Api::class)

package com.archestro.composecontactsscreen.ui.common.topbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopBar(
    text: String,
    onBackButtonClicked: () -> Unit
) {

    CenterAlignedTopAppBar(
        title = { Text(text) },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar("Select or Enter Mobile No.") {}
}