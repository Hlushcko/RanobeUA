package com.ranobeua.firebase

import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ranobeua.firebase.other.TeamBase
import com.ranobeua.firebase.other.data.Team
import com.ranobeua.firebase.ranobe.ChapterBase
import com.ranobeua.firebase.ranobe.RanobeBase
import com.ranobeua.firebase.ranobe.data.Chapter
import com.ranobeua.firebase.ranobe.data.Ranobe
import com.ranobeua.firebase.user.UserBase
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

    private fun addChepter(){
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