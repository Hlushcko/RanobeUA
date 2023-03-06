package com.ranobeua.firebase

import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

import com.ranobeua.base.firebase.other.TeamBase
import com.ranobeua.base.firebase.other.data.Team
import com.ranobeua.base.firebase.ranobe.RanobeBase
import com.ranobeua.base.firebase.ranobe.data.Ranobe
import com.ranobeua.base.firebase.ranobe.data.RanobeTeamInfo
import com.ranobeua.base.firebase.user.UserBase

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.*
import java.util.concurrent.CountDownLatch


@RunWith(JUnit4::class)
class RanobeTests {

    val email = "test.user.email@ranobe.ua.com"
    val password = "passwordUser"

    val firebase = RanobeBase()
    lateinit var ranobe: Ranobe


    @Before
    fun init(){
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().targetContext)

        val latch = CountDownLatch(1)
        UserBase().logIn(email, password){
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()

        initRanobe()
        createRanobe()
    }

    private fun initRanobe(){
        var team: Team? = null
        TeamBase().getTeamByEmailCreator(FirebaseAuth.getInstance().currentUser?.email!!){
            assertEquals(true, it != null)
            team = it!!
        }

        val teamInfo = RanobeTeamInfo(
            "naruto", "saske", "finish",
            team?.name!!, team?.idTeam!!, "personalSiteTeam.ua.com"
        )

        val map = HashMap<String, RanobeTeamInfo>()
        map["first"] = teamInfo
        ranobe = Ranobe("japanese name", "author name", "2000", map)
    }

    private fun createRanobe(){
        var latch = CountDownLatch(1)
        firebase.createRanobe(ranobe){
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()
    }


    @Test
    fun testGetInfo(){
        val latch = CountDownLatch(1)
        firebase.getRanobeInfo(ranobe.originalName){
            assertEquals(ranobe.ranobeTeamInfo.values.first().idTeam, it?.ranobeTeamInfo?.values?.first()?.idTeam)
            latch.countDown()
        }
        latch.await()
    }


    @Test
    fun testUpdateInfoTeam(){
        val newInfo = HashMap<String, Any>()
        newInfo["name"] = "наруто"
        newInfo["description"] = "новий опис"
        newInfo["status"] = "продовжується"
        newInfo["urlTeam"] = "newLink.org.ua"

        var latch = CountDownLatch(1)
        firebase.updateInfoRanobeTeam(ranobe.originalName, ranobe.ranobeTeamInfo.values.first().idTeam, newInfo){
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()

        latch = CountDownLatch(1)
        firebase.getRanobeInfo(ranobe.originalName){
            val info = it?.ranobeTeamInfo?.values?.first()
            assertEquals("наруто", info?.name)
            assertEquals("новий опис", info?.description)
            assertEquals("продовжується", info?.status)
            assertEquals("newLink.org.ua", info?.urlTeam)
            latch.countDown()
        }
        latch.await()
    }


    @Test
    fun testGetRanobeList(){
        val latch = CountDownLatch(1)
        firebase.getRanobeList {
            assertEquals(true, it?.isNotEmpty())
            latch.countDown()
        }
        latch.await()
    }


    @Test
    fun addChapterToRanobe(){
        var latch = CountDownLatch(1)
        RanobeBase.addChapterToRanobe(ranobe.originalName, ranobe.ranobeTeamInfo.values.first().idTeam, "testId"){
            assertEquals(true, it)
            latch.countDown()
        }
        latch.await()

        latch =  CountDownLatch(1)
        firebase.getRanobeInfo(ranobe.originalName){
            val element = it?.ranobeTeamInfo?.values?.first()
            assertEquals(true, element?.chaptersId?.isNotEmpty())
            latch.countDown()
        }
        latch.await()
    }
}