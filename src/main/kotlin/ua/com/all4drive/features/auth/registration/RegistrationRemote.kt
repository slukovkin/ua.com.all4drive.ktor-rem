package ua.com.all4drive.features.auth.registration

@kotlinx.serialization.Serializable
data class RegistrationReceiveRemote(
    val email: String,
    val password: String
)

@kotlinx.serialization.Serializable
data class RegistrationResponseRemote(
    val token :String
)