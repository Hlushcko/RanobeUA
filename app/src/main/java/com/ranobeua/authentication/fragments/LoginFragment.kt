package com.ranobeua.authentication.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ranobeua.R
import com.ranobeua.base.firebase.viewModel.ViewModelUserBase
import com.ranobeua.mainApp.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private val completeMessageResetPassword = "На вашу електронну адресу відправлено лист для відновлення паролю"
    private val errorFirebase = "Ваш профіль не було знайдено або firebase не відповідає, спробуйте знову"
    private val isNotCorrect = "пошта або пароль який ви ввели не відповідає критеріям"


    private var email: EditText? = null
    private var password: EditText? = null
    private var registration: TextView? = null
    private var setPassword: TextView? = null
    private var login: Button? = null

    private var userBase: ViewModelUserBase? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        initView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initElements()
    }


    private fun initElements(){
        userBase = ViewModelProvider(this)[ViewModelUserBase::class.java]
    }

    private fun initView(view: View){
        email = view.findViewById(R.id.editTextTextEmailAddress)
        password = view.findViewById(R.id.firstPassword)
        registration = view.findViewById(R.id.registration)
        setPassword = view.findViewById(R.id.resetPassword)
        login = view.findViewById(R.id.loginUser)
    }

    private fun initObserve(){
//        initEmail()
//        initPassword()
        initRegistration()
        initSetPassword()
        initLogin()
    }

    // TODO: можливо в майбутньому використати ці функції для
    //  перевірки на null та чи мають вони потрібну їм кількість
    //  символів.
//    private fun initEmail(){
//        email?.setOnClickListener {
//            lifecycleScope.launch {
//                val text = email!!.text.toString()
//                userBase.
//            }
//        }
//    }
//
//    private fun initPassword(){
//        password?.setOnClickListener{
//            lifecycleScope.launch {
//
//            }
//        }
//    }

    private fun initRegistration(){
        registration?.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {

            }
        }
    }

    private fun initSetPassword(){
        setPassword?.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                userBase?.resetPassword(email?.text.toString()){
                    when (it) {
                        true -> {
                            Toast.makeText(activity?.applicationContext, completeMessageResetPassword, Toast.LENGTH_LONG).show()
                        }
                        false -> {
                            Toast.makeText(activity?.applicationContext, errorFirebase, Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(activity?.applicationContext, isNotCorrect, Toast.LENGTH_LONG).show()
                        }
                    }
                }


            }
        }
    }

    private fun initLogin() {
        login?.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                userBase?.login(email?.text.toString(), password?.text.toString()) {
                    when (it) {
                        true -> {
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        false -> {
                            Toast.makeText(activity?.applicationContext, errorFirebase, Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(activity?.applicationContext, isNotCorrect, Toast.LENGTH_LONG).show()
                        }
                    }
                }


            }
        }
    }

}