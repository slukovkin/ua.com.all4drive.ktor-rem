package ua.com.all4drive.di

import org.koin.dsl.module
import ua.com.all4drive.controllers.UserController
import ua.com.all4drive.repositiries.UserRepository

val userModule = module {

    single {
        UserController(userRepository = get())
    }

    single {
        UserRepository(database = get())
    }
}
