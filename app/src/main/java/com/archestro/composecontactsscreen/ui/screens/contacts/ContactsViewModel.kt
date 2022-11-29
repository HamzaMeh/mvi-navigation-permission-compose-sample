@file:OptIn(ExperimentalPermissionsApi::class)

package com.archestro.composecontactsscreen.ui.screens.contacts

import android.database.Cursor
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.archestro.composecontactsscreen.R
import com.archestro.composecontactsscreen.utils.permissionhandler.PermissionHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactsViewModel  constructor(
    private val permissionHandler: PermissionHandler
    ): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect


    init {
        permissionHandler
            .state
            .onEach { stateHandler ->
                _state.update { it.copy(multiplePermissionsState = stateHandler.multiplePermissionsState) }
            }
            .catch { Timber.e(it) }
            .launchIn(viewModelScope)
    }

    data class State(
        val permissionRequestInFlight: Boolean = false,
        val hasContactsPermission: Boolean = false,
        val multiplePermissionsState: MultiplePermissionsState? = null,
        val permissionAction: PermissionHandler.Action = PermissionHandler.Action.NO_ACTION
    )

    sealed class Event {

        data class Error(val exception: Exception) : Event()

        object PermissionRequired : Event()
    }

    sealed class Effect {

        data class ShowMessage(val message: Int = R.string.something_went_wrong) : Effect()
        data class NavigateTo(val route: String) : Effect()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.Error -> onError()
            Event.PermissionRequired -> onPermissionRequired()
        }
    }

    private fun onError(){
        viewModelScope.launch {
            _effect.emit(Effect.ShowMessage())
        }
    }

    private fun onPermissionRequired(){
        permissionHandler.onEvent(PermissionHandler.Event.PermissionRequired)
    }


    /*fun fetchContacts() {
        viewModelScope.launch {
            val contactsListAsync = async { getPhoneContacts() }
            val contactNumbersAsync = async { getContactNumbers() }

            val contacts = contactsListAsync.await()
            val contactNumbers = contactNumbersAsync.await()

            contacts.forEach {
                contactNumbers[it.id]?.let { numbers ->
                    it.numbers = numbers
                    numbers.forEach { cell ->
                        it.mobileNumber = cell
                    }
                }
            }
            //_contactsLiveData.postValue(contacts)
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
*/
    data class ContactModel(
        val id:String,
        val name:String,
        val number:String
    )

}