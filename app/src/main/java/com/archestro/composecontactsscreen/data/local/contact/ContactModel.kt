package com.archestro.composecontactsscreen.data.local.contact

data class ContactModel(
    val id:String,
    val name:String,
    var number:String? = null
){
    var numbers = ArrayList<String>()
}
