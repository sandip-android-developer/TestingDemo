package com.example.unittestingdemo

object RegistrationUtils {
    private val existingUser = listOf("sandy", "kumar")
    fun validationRegistrationInput(
        userName: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (userName.isEmpty() || password.isEmpty()) return false
        if (userName in existingUser) return false
        if (password != confirmPassword) return false
        if (password.count { it.isDigit() } < 2) return false
        return true
    }
}