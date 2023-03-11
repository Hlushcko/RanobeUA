package com.ranobeua.authentication.fragments

import android.os.Bundle
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistrationFragment : Fragment() {

    private val completeRegistration = "На вашу електронну адресу відправлено лист для підтвердження реєстрації"
    private val errorFirebase = "користувач з таким іменем або поштою вже існує або сталась помилка в роботі firebase, спробуйте знову"
    private val isNotCorrect = "пошта або пароль який ви ввели не відповідає критеріям"
    private val passwordsNotSame = "паролі не збігаються, будь ласка напишіть їх коректно"

    private var name: EditText? = null
    private var email: EditText? = null
    private var firstPassword: EditText? = null
    private var secondPassword: EditText? = null
    private var login: TextView? = null
    private var registration: Button? = null

    private var userBase: ViewModelUserBase? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container, false)

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
        name = view.findViewById(R.id.editNameUser)
        email = view.findViewById(R.id.userEmailEditText)
        firstPassword = view.findViewById(R.id.firstPassword)
        secondPassword = view.findViewById(R.id.secondPasswordEdit)
        registration = view.findViewById(R.id.buttonRegistration)
        login = view.findViewById(R.id.goUserAccount)
    }


    private fun initObserve(){
        initRegistration()
        initLogin()
    }


    private fun initRegistration(){
        registration?.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {

                if (firstPassword?.text.toString() != secondPassword?.text.toString()) {
                    Toast.makeText(activity?.applicationContext, passwordsNotSame, Toast.LENGTH_LONG).show()
                } else {

                    userBase?.registration(name?.text.toString(), email?.text.toString(), firstPassword?.text.toString()) {
                        when (it) {
                            true -> {
                                Toast.makeText(activity?.applicationContext, completeRegistration, Toast.LENGTH_LONG).show()
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


    private fun initLogin() {
        login?.setOnClickListener {
            val frag = activity?.supportFragmentManager?.beginTransaction()
            frag?.replace(R.id.authenticationUser, LoginFragment())
        }
    }


}