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

        get("/auth/users") {
            val users = UserService.Users.getAll()
            call.respond(HttpStatusCode.OK, users.map { "User with email: ${it.email}" })
        }

        get("/auth/users/{email}") {
            val email = call.parameters["email"] ?: "default@gmail.com"
            val user = UserService.Users.findUserByEmail(email)
            if (user != null && user.email != "") {
                call.respond(HttpStatusCode.OK, "User: ${user.email} contains to database")
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found in a database")
            }
            call.respond(HttpStatusCode.NotFound, "User not found in a database")
        }

        patch("/auth/users/{email}") {
            val email = call.parameters["email"] ?: ""
            val user = call.receive<UserDTO>()
            UserService.Users.updateUserWithEmail(email, user)
            call.respond(HttpStatusCode.OK)
        }

        post("/auth/register") {
            val candidate = call.receive<UserDTO>()
            val password = candidate.password
            candidate.password =
                BCrypt.withDefaults().hash(10, password.toByteArray()).toString()
            val id = UserService.Users.insert(candidate)
            call.respond(HttpStatusCode.Created, "User created with ID: $id")
        }

        delete("/auth/users/{email}") {
            val email = call.parameters["email"] ?: ""
            UserService.Users.deleteUserByEmail(email)
            call.respond(HttpStatusCode.OK)
        }
    }
}
