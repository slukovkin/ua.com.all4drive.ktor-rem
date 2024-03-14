package ua.com.all4drive.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ua.com.all4drive.mock_database.LocalCache

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Welcome to KTOR")
        }

        get("/auth/users") {
            val users = LocalCache.localCache
            users.map {
                call.respondText("User with email: ${it.email}")
            }
        }
    }
}
