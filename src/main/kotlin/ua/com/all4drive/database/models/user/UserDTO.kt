package ua.com.all4drive.database.models.user

@kotlinx.serialization.Serializable
data class UserDTO(
    val email: String,
    var password: String
)
