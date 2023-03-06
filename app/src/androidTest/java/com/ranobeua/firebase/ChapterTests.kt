package com.ranobeua.firebase

import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

import com.ranobeua.base.firebase.other.TeamBase
import com.ranobeua.base.firebase.other.data.Team
import com.ranobeua.base.firebase.ranobe.ChapterBase
import com.ranobeua.base.firebase.ranobe.data.Chapter
import com.ranobeua.base.firebase.user.UserBase
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.CountDownLatch


@RunWith(JUnit4::class)
class ChapterTests {

    val email = "test.user.email@ranobe.ua.com"
    val password = "passwordUser"

    val firebase = ChapterBase()


    @Before
    fun init() {
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().targetContext)

        val latch = CountDownLatch(1)
        UserBase().logIn(email, password) {
            Assert.assertEquals(true, it)
            latch.countDown()
        }
        latch.await()
    }

    @Test
    fun addChepter(){
        var team: Team? = null
        TeamBase().getTeamByEmailCreator(FirebaseAuth.getInstance().currentUser?.email!!){
            Assert.assertEquals(true, it != null)
            team = it!!
        }
        val chapter = Chapter(1, "test name", 136261366, )

        val latch = CountDownLatch(1)
        firebase.addChapter("japanese name", team?.idTeam!!, "text comment", chapter){
            Assert.assertEquals(true, it)
            latch.countDown()
        }
        latch.await()
    }



}