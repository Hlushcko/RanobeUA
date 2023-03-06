package com.ranobeua.firebase

import android.provider.ContactsContract.RawContacts.Data
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ranobeua.firebase.other.TeamBase
import com.ranobeua.firebase.other.data.Team
import com.ranobeua.firebase.user.UserBase

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.CountDownLatch

@RunWith(JUnit4::class)
class FirebaseTeamTests {

    val email = "test.user.email@ranobe.ua.com"
    val password = "passwordUser"

    val baseUser = UserBase()
    val teamBase = TeamBase()
    val team = Team(
        "testTeam",
        "name",
        "my description",
        "team.ranobe@ranobe.ua.com",
        Date().time
    )

    @Before
    fun init(){
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().targetContext)

        val latch = CountDownLatch(1)
        baseUser.logIn(email, password){
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()
    }

    @Test
    fun createCommand(){
        teamBase.createTeam(team){
            assertEquals(true, it)
        }

        val latch = CountDownLatch(1)
        teamBase.getTeam(team.idTeam){
            assertEquals(team, it)
            latch.countDown()
        }
        latch.await()
    }


    @Test
    fun getTeamByEmail(){
        teamBase.createTeam(team){
            assertEquals(true, it)
        }

        val latch = CountDownLatch(1)
        teamBase.getTeamByEmailCreator(team.emailCreator){
            assertEquals(team, it)
            latch.countDown()
        }
        latch.await()
    }


    @Test
    fun setTeamDescription(){
        teamBase.createTeam(team){
            assertEquals(true, it)
        }

        val info = "new description"
        var latch = CountDownLatch(1)

        teamBase.setTeamDescription(team.idTeam, info){
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()

        latch = CountDownLatch(1)
        teamBase.getTeamByEmailCreator(team.emailCreator){
            assertEquals(info, it?.description)
            latch.countDown()
        }
        latch.await()
    }


}