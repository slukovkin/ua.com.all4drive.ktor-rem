package ua.com.all4drive.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/rem",
        user = "root",
        driver = "com.mysql.cj.jdbc.Driver",
        password = "86818682"
    )
}
