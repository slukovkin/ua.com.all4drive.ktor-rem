package ua.com.all4drive.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/rem",
        user = "scorp",
        driver = "org.postgresql.Driver",
        password = "8682"
    )
}
