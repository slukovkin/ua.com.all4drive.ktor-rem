package ua.com.all4drive.database.models.user

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

data class User(val userId: String, val email: String, val password: String)
class UserService(database: Database) {
    object Users : Table("users") {
        private val userId = Users.varchar("id", 150)
        private val email = Users.varchar("email", 100)
        private val password = Users.varchar("password", 150)

        override val primaryKey = PrimaryKey(userId)

        private suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

        suspend fun getAll(): List<User> = dbQuery {
            dbQuery {
                Users.selectAll().map(::resultRowToUser)
            }
        }

        fun findUserByEmail(email: String): User? {
            return transaction {
                Users.select { Users.email eq email }.map { toDomain(it) }.firstOrNull()
            }
        }

        suspend fun insert(userDTO: UserDTO): String = dbQuery {
            transaction {
                Users.insert {
                    it[userId] = UUID.randomUUID().toString()
                    it[email] = userDTO.email
                    it[password] = userDTO.password
                }[userId]
            }
        }

        suspend fun deleteUserByEmail(email: String) {
            dbQuery {
                Users.deleteWhere { this.email.eq(email) }
            }
        }

        fun updateUserWithEmail(email: String, userDTO: UserDTO): User? {
            transaction {
                Users.update({
                    Users.email.eq(email)
                }) { user ->
                    if (userDTO.email.isNotBlank()) {
                        user[Users.email] = userDTO.email
                    }
                    if (userDTO.password.isNotBlank()) {
                        user[password] = userDTO.password
                    }
                }
            }
            return findUserByEmail(userDTO.email)
        }

        private fun toDomain(row: ResultRow): User {
            return User(
                userId = row[userId], email = row[email], password = row[password]
            )
        }

        private fun resultRowToUser(row: ResultRow) = User(
            userId = row[userId],
            email = row[email],
            password = row[password],
        )
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }
}

