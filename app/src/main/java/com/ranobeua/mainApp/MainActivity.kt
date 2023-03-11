package com.ranobeua.mainApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ranobeua.R
import com.ranobeua.mainApp.fragments.ranobe.RanobeListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startHomeFragment()
    }


    private fun startHomeFragment(){
        val frag = supportFragmentManager.beginTransaction()
        frag.addToBackStack(null)
        frag.replace(R.id.mainFrame, RanobeListFragment())
        frag.commit()
    }

}