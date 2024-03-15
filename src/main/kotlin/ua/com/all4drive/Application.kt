package ua.com.all4drive

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ua.com.all4drive.features.auth.login.loginRouting
import ua.com.all4drive.features.auth.registration.registerRouting
import ua.com.all4drive.plugins.*

fun main() {

    embeddedServer(CIO, port = 3000, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureRouting()
    loginRouting()
    registerRouting()
}
