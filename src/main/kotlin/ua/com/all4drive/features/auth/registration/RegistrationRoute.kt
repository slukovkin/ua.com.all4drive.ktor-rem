package ua.com.all4drive.features.auth.registration

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ua.com.all4drive.mock_database.LocalCache

fun Application.registerRouting() {
    routing {
        post ("/auth/register") {
            val candidate = call.receive<RegistrationReceiveRemote>()
            LocalCache.localCache.add(candidate)
            call.respondText("Registration user with email: ${candidate.email}")
        }
    }
}
