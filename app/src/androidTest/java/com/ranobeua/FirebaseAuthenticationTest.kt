package com.ranobeua

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ranobeua.firebase.user.UserBase

import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Test


@RunWith(AndroidJUnit4::class)
class FirebaseAuthenticationTest {

    private val firebase = UserBase();

    @Test
    fun register(){
        val email = "email.test.user@ranobe.ua.com"
        val password = "testPassword"
        val name = "testUser"

        firebase.registerAccount(name, email, password)
        firebase.getUserInfo(email) { result ->
            assertEquals(email, result?.email)
            assertEquals(name, result?.name)
        }

        firebase.getUserInfo(email){ result ->
            println(result?.name + " | " + result?.level)
        }
    }

    @Test
    fun logIn(){
        val email = "test.user.email@ranobe.ua.com"
        val password = "passwordUser"

        firebase.logIn(email, password){ result ->
            assertEquals(true, result)
        }

        firebase.signOut()
        assertEquals(false, firebase.getStatusLogin())
    }

}