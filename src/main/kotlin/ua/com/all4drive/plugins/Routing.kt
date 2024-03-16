package ua.com.all4drive.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ua.com.all4drive.database.models.user.UserDTO
import ua.com.all4drive.database.models.user.UserService

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Welcome to KTOR")
        }
    }
}
