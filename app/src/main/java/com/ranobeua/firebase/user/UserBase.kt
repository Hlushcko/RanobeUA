package com.ranobeua.firebase.user


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ranobeua.firebase.user.data.User


class UserBase {

    companion object {
        val auth = FirebaseAuth.getInstance()
        val userBase = FirebaseDatabase.getInstance().getReference("users")

        fun addElementToUser(value: String, key: String, callable: (Boolean?) -> Unit){
            val email = auth.currentUser?.email
            if(email != null) {
                userBase.orderByChild("email").equalTo(email)
                    .addListenerForSingleValueEvent(object: ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userPath = snapshot.children.firstOrNull()?.key
                            if (userPath != null && userPath.isNotEmpty()) {
                                val comments = userBase.child(userPath).child(key)
                                comments.push().setValue(value)
                                callable(true)
                            }else{
                                callable(false)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            throw Exception("error connect to firebase")
                        }
                    })
            }else {
                throw Exception("user email is null!")
            }
        }
    }

    // TODO: name має складатись виключно із символів, без знаків ?.!№;":%* та інших, виключно текст
    fun registerAccount(name: String, email: String, password: String, callback: (Boolean?) -> Unit){
        if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                if(it.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()
                    addInfoToDatabase(name, email)
                    callback(true)
                }else{
                    callback(false)
                }

            }
        }else{
            callback(false)
        }
    }


    private fun addInfoToDatabase(name: String, email: String){
        val user = userBase.child(name)
        user.child("email").setValue(email)
        user.child("name").setValue(name)
        user.child("readChapters").setValue(0)
        user.child("level").setValue("новачок")
        user.child("commentsId").push()
        user.child("teamId").push()
    }


    // TODO: клас який буде це викликати має містити наступний код:
    //  getUserInfo("a"){ data ->
    //                if (data != null){
    //                   не нуль, обробляєм
    //                }else{
    //                    нуль, кажемо це користувачу
    //                }
    //            }
    fun getUserInfoByName(name: String, callback: (User?) -> Unit){
        userBase.child(name).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = snapshot.getValue(User::class.java)
                callback(result)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }


    fun getUserInfoByEmail(email: String, callback: (User?) -> Unit){
        userBase.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.children.firstOrNull()?.getValue(User::class.java)
                    callback(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }


    // TODO: потрібно дати можливість міняти нік, кількість прочитаних глав,
    //  рівень та id команди в які може бути учасник. Потім це буде перевірятись.
    //  В хеш таблицю в String пишемо наприклад "name", а Any буде містити нове значення.
    fun updateInfoUser(name: String, hashMap: HashMap<String, Any>){
        userBase.child(name).updateChildren(hashMap)
    }


    fun logIn(email: String, password: String, callable: (Boolean?) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                callable(it.isSuccessful)
            }
        }else{
            throw Exception("user is not created or not verification")
        }
    }


    fun signOut(){
        auth.signOut()
    }

    fun getStatusLogin() : Boolean {
        return auth.currentUser != null && auth.currentUser?.isEmailVerified!!
    }

    fun resetPassword(email: String){
        auth.sendPasswordResetEmail(email)
    }

    fun deleteAccount(){
        auth.currentUser?.delete()
    }

}