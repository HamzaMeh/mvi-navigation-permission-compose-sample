package com.archestro.composecontactsscreen.ui.features.contacts.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.archestro.composecontactsscreen.data.local.contact.ContactModel
import com.archestro.composecontactsscreen.ui.features.contacts.ContactsViewModel
import com.archestro.composecontactsscreen.ui.theme.NeutralN10
import com.archestro.composecontactsscreen.ui.theme.NeutralN30
import com.archestro.composecontactsscreen.ui.theme.Typography
import com.archestro.composecontactsscreen.utils.Utils


@Composable
fun ContactItem(contact: ContactModel, onItemClick: (ContactModel) -> Unit){
    Row(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 16.dp)
        .clickable {
            onItemClick(contact)
        }
    ) {

        NameInitials(initials = Utils.getNameInitials(contact.name,2))

        Spacer(modifier = Modifier.size(10.dp))

        Column(
            modifier = Modifier.offset(y= (-3f).dp)
        ) {
            ContactName(contact.name)
            ContactNumber(contact.number!!)
        }

    }
}


@Composable
fun NameInitials(initials:String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(NeutralN10),
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = initials,
            color = NeutralN30
        )
    }
}

@Composable
fun ContactName(name:String){
    Text(
        text = name,
        style = Typography.body1,
        color = Color.Black
    )
}

@Composable
fun ContactNumber(number:String){
    Text(text = number,
        style = Typography.body1,
        color = NeutralN30
    )
}


@Preview(showBackground = true)
@Composable
private fun UsersListItemPreview() {
    ContactItem(contact = ContactModel("1","Username","042 3456789"),{})
}