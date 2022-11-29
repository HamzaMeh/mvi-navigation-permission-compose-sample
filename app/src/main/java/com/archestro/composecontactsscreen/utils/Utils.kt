package com.archestro.composecontactsscreen.utils

import java.util.*

object Utils {

    fun getNameInitials(name:String, letters: Int) : String{
        var profileLetter=""
        if(name.isNotEmpty())
        {
            if (letters == 1) {
                profileLetter =
                    name.uppercase(Locale.ROOT)[0]
                        .toString() + ""
            } else {
                val separated: Array<String> =
                    name.split(" ").toTypedArray()
                if (separated.size > 1) {
                    profileLetter = ""
                    for (s in separated) if (s != "") profileLetter += s.uppercase()[0]
                } else if (separated.size == 1) {
                    profileLetter =
                        separated[0].uppercase()[0].toString() + ""
                } else {
                    profileLetter = ""
                }
            }
        }

        return profileLetter

    }
}