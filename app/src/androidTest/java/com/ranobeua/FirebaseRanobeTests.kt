package com.ranobeua

import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.ranobeua.firebase.user.UserBase

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.*
import java.util.concurrent.CountDownLatch


@RunWith(JUnit4::class)
class FirebaseRanobeTests {

    private val firebase = UserBase();

    @Before
    fun init(){
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun testCreate(){
//        var latch = CountDownLatch(1)
//        latch.countDown()
//        latch.await()
//
//        assertEquals()
    }



}