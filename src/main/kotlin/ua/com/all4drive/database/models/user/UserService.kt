package ua.com.all4drive.database.models.user

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserService(database: Database) {
    object Users : Table("users") {
        private val userId = Users.varchar("id", 150)
        private val email = Users.varchar("email", 100)
        private val password = Users.varchar("password", 150)

        override val primaryKey = PrimaryKey(userId)

        private suspend fun <T> dbQuery(block: suspend () -> T): T =
            newSuspendedTransaction(Dispatchers.IO) { block() }

        suspend fun insert(userDTO: UserDTO): String = dbQuery {
            transaction {
                Users.insert {
                    it[userId] = UUID.randomUUID().toString()
                    it[email] = userDTO.email
                    it[password] = userDTO.password
                }[userId]
            }
        }

        suspend fun deleteUserById(email: String) {
            dbQuery {
                Users.deleteWhere { this.email.eq(email) }
            }
        }
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }
}

