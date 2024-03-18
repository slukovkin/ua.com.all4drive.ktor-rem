package ua.com.all4drive.config

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.koin.core.context.startKoin
import ua.com.all4drive.di.userModule
import ua.com.all4drive.plugins.configureRouting
import ua.com.all4drive.routes.userRoutes

const val SERVER_PORT = 3000
const val SERVER_DB_URL = "jdbc:postgresql://localhost:5432/rem"
const val DB_USERNAME = "scorp"
const val DB_PASSWORD = "8682"
const val DB_DRIVER = "org.postgresql.Driver"

fun setup(): BaseApplicationEngine {
    DbConfig.setup(SERVER_DB_URL, DB_USERNAME, DB_PASSWORD)
    return server(CIO)
}

fun server(
    engine: ApplicationEngineFactory<BaseApplicationEngine,
            out ApplicationEngine.Configuration>
): BaseApplicationEngine {
    return embeddedServer(
        engine,
        port = SERVER_PORT,
        module = Application::mainModule
    )
}

fun Application.mainModule() {

    startKoin {
        configureRouting()
        userRoutes()
        modules(
            listOf(
                userModule
            )
        )
    }
}