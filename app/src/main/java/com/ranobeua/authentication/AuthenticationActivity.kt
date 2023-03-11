package com.ranobeua.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ranobeua.R
import com.ranobeua.authentication.fragments.LoginFragment

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        startLoginFragment()
    }


    private fun startLoginFragment(){
        val frag = supportFragmentManager.beginTransaction()
        frag.addToBackStack(null)
        frag.replace(R.id.authenticationUser, LoginFragment())
        frag.commit()
    }

}