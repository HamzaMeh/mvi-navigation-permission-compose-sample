package com.archestro.composecontactsscreen.ui.features.contacts.contract

import com.archestro.composecontactsscreen.base.ViewEvent
import com.archestro.composecontactsscreen.base.ViewSideEffect
import com.archestro.composecontactsscreen.base.ViewState
import com.archestro.composecontactsscreen.data.local.contact.ContactModel

class ContactContract {
        sealed class Event : ViewEvent {
            object Retry : Event()
            data class ContactSelected(val contact:ContactModel) : Event()
        }

        data class State(
            val contacts: List<ContactModel>,
            val isLoading: Boolean,
            val isCompleted:Boolean,
            val isError: Boolean
        ) : ViewState

        sealed class Effect : ViewSideEffect {

            object DataWasLoaded : Effect()

            sealed class Navigation : Effect() {
                data class ContactSelected(val id: String): Navigation()
            }
        }


}