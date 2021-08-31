package com.example.unittestingdemo

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RegistrationUtilsTest {
    @Test
    fun `empty username return false`() {
        val result = RegistrationUtils.validationRegistrationInput("", "123", "123")
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password return true`() {
        val result = RegistrationUtils.validationRegistrationInput("sandip", "123", "123")
        assertThat(result).isTrue()
       /* assertThat("hello").isEqualTo("hello")*/
    }
    @Test
    fun `username already exist return false`() {
        val result = RegistrationUtils.validationRegistrationInput("sandy", "123", "123")
        assertThat(result).isFalse()
    }
    @Test
    fun `empty password return false`() {
        val result = RegistrationUtils.validationRegistrationInput("sandy", "", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly confirm password  return false`() {
        val result = RegistrationUtils.validationRegistrationInput("sandy", "1234", "12345")
        assertThat(result).isFalse()
    }

    @Test
    fun `less than 2 digit password  return false`() {
        val result = RegistrationUtils.validationRegistrationInput("sandy", "123456", "123456")
        assertThat(result).isFalse()
    }
}