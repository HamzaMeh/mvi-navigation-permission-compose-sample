package com.archestro.composecontactsscreen.ui.screens.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.archestro.composecontactsscreen.ui.common.SearchView
import com.archestro.composecontactsscreen.ui.theme.BackgroundGray
import com.archestro.composecontactsscreen.ui.theme.NeutralN10
import com.archestro.composecontactsscreen.ui.theme.NeutralN30
import com.archestro.composecontactsscreen.ui.theme.Typography
import com.archestro.composecontactsscreen.utils.Utils


@Composable
fun ContactItem(
    contacts : List<ContactsViewModel.ContactModel>
){
    Box(
        modifier = Modifier
            .background(BackgroundGray)
            .fillMaxSize()
    ) {
        LazyColumn {
            item{
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                SearchView("Enter Name or Number") {
                    //todo for search click
                }
                Spacer(modifier = Modifier
                    .padding(vertical = 16.dp)
                )
            }
            items(contacts){ contact ->
                Row(modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {

                    NameInitials(initials = Utils.getNameInitials(contact.name,2))

                    Spacer(modifier = Modifier.size(10.dp))
                    
                    Column(
                        modifier = Modifier.offset(y= (-3f).dp)
                    ) {
                        ContactName(contact.name)
                        ContactNumber(contact.number)
                    }

                }
                Divider(
                    color= Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
            }

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




