package com.ranobeua

import com.ranobeua.firebase.user.UserBase
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestFirebaseAuthentication {

    //fun testSignInWithEmailAndPassword() {
    //    val email = "user@example.com"
    //    val password = "password123"
    //
    //    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    //        .addOnCompleteListener { task ->
    //            if (task.isSuccessful) {
    //                // Автентифікація успішна
    //            } else {
    //                // Автентифікація не вдалась
    //            }
    //        }
    //}

    private val email = ""
    private val password = ""
    @Test
    fun tryAuthentication(){
        assertEquals(false, UserBase().getStatusLogin())
        UserBase().logIn(email, password)
        assertEquals(true, UserBase().getStatusLogin())
    }


}