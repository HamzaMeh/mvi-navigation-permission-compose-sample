@file:OptIn(ExperimentalPermissionsApi::class)

package com.archestro.composecontactsscreen.ui.features.contacts

import android.app.Application
import android.database.Cursor
import android.provider.ContactsContract
import androidx.lifecycle.viewModelScope
import com.archestro.composecontactsscreen.ComposeApplication
import com.archestro.composecontactsscreen.R
import com.archestro.composecontactsscreen.base.BaseViewModel
import com.archestro.composecontactsscreen.data.local.contact.ContactModel
import com.archestro.composecontactsscreen.ui.features.contacts.contract.ContactContract
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionHandler
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactsViewModel  constructor(
    val permissionState: PermissionState,
    val permissionHandler: PermissionHandler,
    private val mApplication: Application,
    ): BaseViewModel<ContactContract.Event,ContactContract.State,ContactContract.Effect>() {

    init {
        permissionState.initPermissionState(viewModelScope,permissionHandler)
    }

    override fun setInitialState() = ContactContract.State(
        contacts = emptyList(),
        isLoading = false,
        isCompleted = false,
        isError = false,
    )

    override fun handleEvents(event: ContactContract.Event) {
        when(event){
            is ContactContract.Event.Retry ->{}
            is ContactContract.Event.ContactSelected ->{ ContactContract.Effect.Navigation.ContactSelected(event.contact.id)}

        }
    }


    fun fetchContacts() {
        val contactsList = mutableListOf<ContactModel>()
        viewModelScope.launch {
            setState { copy(isLoading=true, isCompleted = false) }
            val contactsListAsync = async { getPhoneContacts() }
            val contactNumbersAsync = async { getContactNumbers() }

            val contacts = contactsListAsync.await()
            val contactNumbers = contactNumbersAsync.await()

            contacts.forEach {
                contactNumbers[it.id]?.let { numbers ->
                    it.numbers = numbers
                    numbers.forEach { cell ->
                        it.number = cell
                    }
                }
                contactsList.add(it)
            }
            setState { copy(contacts = contactsList, isLoading=false, isCompleted=true) }
            setEffect { ContactContract.Effect.DataWasLoaded }
        }
    }

    private fun getPhoneContacts(): ArrayList<ContactModel> {
        val contactsList = ArrayList<ContactModel>()
        val contactsCursor = mApplication.contentResolver?.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if (contactsCursor != null && contactsCursor.count > 0) {
            val idIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val phoneValueIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex)
                val name = contactsCursor.getString(nameIndex)
                val numberExist = contactsCursor.getString(phoneValueIndex)
                if (name != null && numberExist == "1") {
                    contactsList.add(ContactModel(id, name))
                }
            }
            contactsCursor.close()
        }
        return contactsList
    }

    private fun getContactNumbers(): HashMap<String, ArrayList<String>> {
        val contactsNumberMap = HashMap<String, ArrayList<String>>()
        val phoneCursor: Cursor? = mApplication.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (phoneCursor != null && phoneCursor.count > 0) {
            val contactIdIndex =
                phoneCursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex =
                phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (phoneCursor.moveToNext()) {
                val contactId = phoneCursor.getString(contactIdIndex)
                val number: String = phoneCursor.getString(numberIndex)
                //check if the map contains key or not, if not then create a new array list with number
                if (contactsNumberMap.containsKey(contactId)) {
                    contactsNumberMap[contactId]?.add(number)
                } else {
                    contactsNumberMap[contactId] = arrayListOf(number)
                }
            }
            //contact contains all the number of a particular contact
            phoneCursor.close()
        }
        return contactsNumberMap
    }

}