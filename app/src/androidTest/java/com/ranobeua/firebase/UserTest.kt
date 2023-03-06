package com.ranobeua.firebase

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
class UserTest {

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
        firebase.getUserInfoByName(name) { result ->
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
    fun getUserByEmail(){
        val email = "realtime.test.user@ranobe.ua.com"
        val password = "testPassword"
        val name = "testName"
        var latch = CountDownLatch(1)

        firebase.registerAccount(name, email, password){ it ->
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()

        latch = CountDownLatch(1)
        firebase.getUserInfoByEmail(email){ result ->
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
        val email = "update.test.user@ranobe.ua.com"
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
        firebase.getUserInfoByName(name) { result ->
            assertEquals(email, result?.email)
            assertEquals(name, result?.name)
            assertEquals("продвинутий", result?.level)
            assertEquals(1, result?.readChapters)
            latch.countDown()
        }
        latch.await()

        firebase.deleteAccount()
    }


    @Test
    fun addComment(){
        //this account you need a create in your base.
        val email = "comment.test.user@ranobe.ua.com"
        val password = "passwordUser"
        var latch = CountDownLatch(1)

        firebase.logIn(email, password) { result ->
            assertEquals(true, result)
            latch.countDown()
        }
        latch.await()


        latch = CountDownLatch(1)
        UserBase.addElementToUser("testComment", "commentsId"){ result ->
            assertEquals(true, result)
            latch.countDown()
        }
        latch.await()

        latch = CountDownLatch(1)
        firebase.getUserInfoByEmail(email) { result ->
            assertEquals(email, result?.email)
            assertEquals("новачок", result?.level)
            assertEquals(0, result?.readChapters)
            assertEquals(true, result?.commentsId?.size!! > 0)
            latch.countDown()
        }
        latch.await()
    }


    @Test
    fun addTeam(){
        //this account you need a create in your base.
        val email = "comment.test.user@ranobe.ua.com"
        val password = "passwordUser"
        var latch = CountDownLatch(1)

        firebase.logIn(email, password) { result ->
            assertEquals(true, result)
            latch.countDown()
        }
        latch.await()


        latch = CountDownLatch(1)
        UserBase.addElementToUser("testTeam", "teamId"){ result ->
            assertEquals(true, result)
            latch.countDown()
        }
        latch.await()

        latch = CountDownLatch(1)
        firebase.getUserInfoByEmail(email) { result ->
            assertEquals(email, result?.email)
            assertEquals("новачок", result?.level)
            assertEquals(0, result?.readChapters)
            assertEquals(true, result?.teamId?.size!! > 0)
            latch.countDown()
        }
        latch.await()
    }

}