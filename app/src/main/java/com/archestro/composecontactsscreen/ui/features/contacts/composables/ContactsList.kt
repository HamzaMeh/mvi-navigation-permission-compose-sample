package com.archestro.composecontactsscreen.ui.features.contacts.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.archestro.composecontactsscreen.data.local.contact.ContactModel
import com.archestro.composecontactsscreen.ui.common.search.SearchView
import com.archestro.composecontactsscreen.ui.features.contacts.ContactsViewModel
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionState

@Composable
fun ContactsList(
    isContactsPermissionGranted : Boolean,
    contacts: List<ContactModel>,
    padding: PaddingValues,
    permissionEvent: (PermissionState.Event) -> Unit,
    onItemClick: (ContactModel) -> Unit,
) {
    if(isContactsPermissionGranted) {
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item {
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                SearchView("Enter Name or Number") {
                }
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                )
            }
            items(contacts) { contact ->
                ContactItem(contact = contact, onItemClick = onItemClick)
                Divider(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
            }
        }
    }else{
        Box(modifier = Modifier.fillMaxSize()) {
            RequestPermission(permissionEvent = permissionEvent)
        }

    }

}

@Composable
fun RequestPermission(
    permissionEvent: (PermissionState.Event) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            shape = MaterialTheme.shapes.small,
            onClick = {
                permissionEvent(PermissionState.Event.PermissionRequired)
            }) {
            Text(text = "Request Contact Permission")

        }
    }
    
}

@Preview(showBackground = true)
@Composable
private fun RequestPermissionPreview(){
    RequestPermission(permissionEvent = {})
}
