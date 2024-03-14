package ua.com.all4drive.features.auth.login

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.loginRouting() {
    routing {
        get("/auth/login") {
            call.respondText("Login")
        }
    }
}
