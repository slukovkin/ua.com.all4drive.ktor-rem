package ua.com.all4drive.features.auth.registration

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ua.com.all4drive.database.models.user.UserDTO
import ua.com.all4drive.database.models.user.UserService

fun Application.registerRouting() {
    routing {
        post ("/auth/register") {
            val candidate = call.receive<UserDTO>()

            val id = UserService.Users.insert(candidate)

            call.respondText("Registration user with email: ${candidate.email} and ID: $id")
        }
    }
}
