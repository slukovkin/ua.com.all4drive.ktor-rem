package ua.com.all4drive.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ua.com.all4drive.controllers.UserController
import ua.com.all4drive.database.models.user.UserDTO
import ua.com.all4drive.utils.stringToHash


fun Application.userRoutes() {

    val userController by inject<UserController>()

    routing {

        get("/") {
            call.respondText("Welcome to KTOR")
        }

        get("/auth/users") {
            val users = userController.getAll()
            call.respond(HttpStatusCode.OK, users.map { "User with email: ${it.email}" })
            return@get
        }

        get("/auth/users{email}") {
            val email = call.parameters["email"] ?: throw IllegalArgumentException("Invalid EMAIL")
            val user = userController.findUserByEmail(email)
            if (user != null && user.email != "") {
                call.respond(HttpStatusCode.OK, "User: ${user.email} contains to database")
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found in a database")
            }
            call.respond(HttpStatusCode.NotFound, "User not found in a database")
            return@get
        }

        patch("/auth/users{email}") {
            val email = call.parameters["email"] ?: throw IllegalArgumentException("Invalid EMAIL")
            val user = call.receive<UserDTO>()
            userController.updateUserWithEmail(email, user)
            call.respond(HttpStatusCode.OK)
            return@patch
        }

        post("/auth/users") {
            val candidate = call.receive<UserDTO>()
            call.respond(HttpStatusCode.OK, "$candidate")
            candidate.password = stringToHash(candidate.password)
            val id = userController.create(candidate)
            call.respond(HttpStatusCode.Created, "User created with ID: $id")
            return@post
        }

        delete("/auth/users{email}") {
            val email = call.parameters["email"] ?: throw IllegalArgumentException("Invalid EMAIL")
            userController.deleteUserByEmail(email)
            call.respond(HttpStatusCode.OK)
            return@delete
        }
    }
}

