package com.archestro.composecontactsscreen.ui.common.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.archestro.composecontactsscreen.ui.theme.NeutralN30

@Composable
fun SearchView(
               searchHint:String,
               onSearch: (String) -> Unit
) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = textState.value,
        onValueChange = { value ->
            textState.value = value
        },
        placeholder={
            Text(text =searchHint,
                color = NeutralN30
            )
        },

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .border(0.5.dp, NeutralN30, shape = RoundedCornerShape(10.dp)),

        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),

        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (textState.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        textState.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            cursorColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),

        keyboardActions = KeyboardActions {
            onSearch(textState.value.text)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview(){
    SearchView(searchHint = "Enter name or number", onSearch = {})
}
