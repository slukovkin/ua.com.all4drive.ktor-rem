package ua.com.all4drive.database.models.user

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UserService(private val database: Database) {
    object Users : Table("users") {
        private val userId = integer("id").autoIncrement()
        private val email = varchar("email", 100)
        private val password = varchar("password", 100)

        override val primaryKey = PrimaryKey(userId)

        suspend fun <T> dbQuery(block: suspend () -> T): T =
            newSuspendedTransaction(Dispatchers.IO) { block() }

        suspend fun insert(userDTO: UserDTO) : Int = dbQuery {
            transaction {
                Users.insert {
                    it[email] = userDTO.email
                    it[password] = userDTO.password
                }[Users.userId]
            }
        }
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }
}

