package ua.com.all4drive.controllers

import ua.com.all4drive.database.models.user.UserDTO
import ua.com.all4drive.repositiries.User
import ua.com.all4drive.repositiries.UserRepository

class UserController(
    private val userRepository: UserRepository
) {

    fun getAll(): List<User> {
        return userRepository.getAll()
    }

    fun findUserByEmail(email: String): User? {
        return userRepository.findUserByEmail(email)
    }

    fun updateUserWithEmail(email: String, user: UserDTO): User? {
        return userRepository.updateUserWithEmail(email, user)
    }

    fun create(user: UserDTO): String {
        return userRepository.create(user)
    }

    fun deleteUserByEmail(email: String) {
        return userRepository.deleteUserByEmail(email)
    }
}