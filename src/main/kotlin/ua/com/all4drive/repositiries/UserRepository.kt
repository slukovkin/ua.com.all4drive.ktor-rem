package ua.com.all4drive.repositiries

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ua.com.all4drive.database.models.user.UserDTO
import java.util.*

data class User(val userId: String, val email: String, val password: String)

internal object Users : Table("users") {
    val userId = Users.varchar("id", 150)
    val email = Users.varchar("email", 100)
    val password = Users.varchar("password", 150)

    override val primaryKey = PrimaryKey(userId)

    fun toDomain(row: ResultRow): User {
        return User(
            userId = row[userId],
            email = row[email],
            password = row[password]
        )
    }

    fun resultRowToUser(row: ResultRow) = User(
        userId = row[userId],
        email = row[email],
        password = row[password],
    )
}

class UserRepository(database: Database) {

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

//    private suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

    fun getAll(): List<User> {
        return transaction {
            Users.selectAll().map(Users::resultRowToUser)
        }
    }

    fun findUserByEmail(email: String): User? {
        return transaction {
            Users.select {
                Users.email eq email
            }
                .map { Users.toDomain(it) }
                .firstOrNull()
        }
    }

    fun create(user: UserDTO): String {
        return transaction {
            Users.insert {
                it[userId] = UUID.randomUUID().toString()
                it[email] = user.email
                it[password] = user.password
            }[Users.userId]
        }
    }

    fun deleteUserByEmail(email: String) {
        transaction {
            Users.deleteWhere { Users.email.eq(email) }
        }
    }

    fun updateUserWithEmail(email: String, user: UserDTO): User? {
        transaction {
            Users.update({
                Users.email.eq(email)
            }) { row ->
                if (user.email.isNotBlank()) {
                    row[Users.email] = user.email
                }
                if (user.password.isNotBlank()) {
                    row[password] = user.password
                }
            }
        }
        return findUserByEmail(user.email)
    }
}