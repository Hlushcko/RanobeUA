package com.ranobeua.base.firebase.viewModel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.ranobeua.base.firebase.user.UserBase
import com.ranobeua.base.firebase.user.data.User

class ViewModelUserBase : ViewModel() {

    val user = UserBase()

    fun registration(name: String, email: String, password: String, callback: (Boolean?) -> Unit){
        if(!checkChar(name) && checkChar(password) && email.isEmailValid()){
            getUserByName(name){
                if (it != null) {
                    user.registerAccount(name, email, password, callback)
                }else{
                    callback(false)
                }
            }
        }else{
            callback(null)
        }
    }

    fun login(email: String, password: String, callable: (Boolean?) -> Unit){
        user.logIn(email, password, callable)
    }

    fun updateUserInfo(name: String, hashMap: HashMap<String, Any>){
        user.updateInfoUser(name, hashMap)
    }

    fun getUserByEmail(email: String, callback: (User?) -> Unit){
        user.getUserInfoByEmail(email, callback)
    }

    fun getUserByName(name: String, callback: (User?) -> Unit){
        user.getUserInfoByName(name, callback)
    }

    fun signOut(){
        user.signOut()
    }

    fun statusLogin() : Boolean{
        return user.getStatusLogin()
    }

    fun resetPassword(email: String, callback: (Boolean?) -> Unit){
        if(email.isEmailValid()) {
            user.resetPassword(email, callback)
        }else{
            callback(null)
        }
    }

    fun deleteAccount(){
        user.deleteAccount()
    }

    private fun checkChar(element: String): Boolean {
        val regex = Regex("[^a-zA-Zа-яА-Я\\d_]")
        return regex.containsMatchIn(element)
    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}