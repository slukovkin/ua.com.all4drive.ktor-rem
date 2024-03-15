package ua.com.all4drive.features.auth.registration

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ua.com.all4drive.database.models.user.UserDTO
import ua.com.all4drive.database.models.user.UserService

fun Application.registerRouting() {
    routing {
        post("/auth/register") {
            val candidate = call.receive<UserDTO>()
            val password = candidate.password
            candidate.password =
                BCrypt.withDefaults().hash(5, password.toByteArray()).toString()
            val id = UserService.Users.insert(candidate)
            call.respond(HttpStatusCode.Created, "User created with ID: $id")
        }

        delete("/auth/users/{email}") {
            val email = call.parameters["email"] ?: ""
            UserService.Users.deleteUserById(email)
            call.respond(HttpStatusCode.Gone)
        }
    }
}
