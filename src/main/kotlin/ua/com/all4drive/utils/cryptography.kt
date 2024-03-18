package ua.com.all4drive.utils

import at.favre.lib.crypto.bcrypt.BCrypt

fun stringToHash(value: String): String {
    return BCrypt.withDefaults().hash(10, value.toByteArray()).toString()
}