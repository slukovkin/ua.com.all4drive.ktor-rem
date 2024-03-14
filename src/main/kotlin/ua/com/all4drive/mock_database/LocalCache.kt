package ua.com.all4drive.mock_database

import ua.com.all4drive.features.auth.registration.RegistrationReceiveRemote

object LocalCache {
    var localCache: MutableList<RegistrationReceiveRemote> = mutableListOf()
}