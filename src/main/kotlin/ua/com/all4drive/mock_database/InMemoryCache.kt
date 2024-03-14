package ua.com.all4drive.mock_database

import ua.com.all4drive.features.auth.registration.RegistrationReceiveRemote

object InMemoryCache {
    var userList: MutableList<RegistrationReceiveRemote> = mutableListOf()
}