package com.ranobeua

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.ranobeua.firebase.user.UserBase

import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch


@RunWith(AndroidJUnit4::class)
class FirebaseAuthenticationTest {

    private val firebase = UserBase();

    @Before
    fun init(){
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().targetContext)
    }


    @Test
    fun register(){
        val email = "registration.test.user@ranobe.ua.com"
        val password = "testPassword"
        val name = "testUser"
        var latch = CountDownLatch(1)

        firebase.registerAccount(name, email, password){ it ->
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()

        latch = CountDownLatch(1)
        firebase.registerAccount(name, email, password){ it ->
            assertEquals(false, it)
            latch.countDown()
        }
        latch.await()

        latch = CountDownLatch(1)
        firebase.getUserInfo(name) { result ->
            assertEquals(email, result?.email)
            assertEquals(name, result?.name)
            assertEquals("новачок", result?.level)
            assertEquals(0, result?.readChapters)
            latch.countDown()
        }
        latch.await()

        firebase.deleteAccount()
    }

    @Test
    fun logIn(){
        val email = "test.user.email@ranobe.ua.com"
        val password = "passwordUser"

        val latch = CountDownLatch(1)
        firebase.logIn(email, password) { result ->
            assertEquals(true, result)
            latch.countDown()
        }
        latch.await()

        firebase.signOut()
        assertEquals(false, firebase.getStatusLogin())
    }

    @Test
    fun updateUserInformation(){
        val email = "registration.test.user@ranobe.ua.com"
        val password = "testPassword"
        val name = "testUser"
        var latch = CountDownLatch(1)

        firebase.registerAccount(name, email, password){ it ->
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()

        val hashMap = HashMap<String, Any>()
        hashMap["level"] = "продвинутий"
        hashMap["readChapters"] = 1
        firebase.updateInfoUser(name, hashMap)

        latch = CountDownLatch(1)
        firebase.getUserInfo(name) { result ->
            assertEquals(email, result?.email)
            assertEquals(name, result?.name)
            assertEquals("новачок", result?.level)
            assertEquals(0, result?.readChapters)
            latch.countDown()
        }
        latch.await()
    }

}