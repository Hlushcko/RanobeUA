package com.ranobeua.firebase.user


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ranobeua.firebase.user.data.User


class UserBase {

    companion object {
        private val auth = FirebaseAuth.getInstance()
        private val userBase = FirebaseDatabase.getInstance().getReference("users")

        fun addCommentToUser(idComment: String){
            val email = auth.currentUser?.email
            if(email != null) {
                val comments = userBase.child(email).child("comments")
                comments.push().setValue(idComment)
            }
        }

    }

    fun registerAccount(name: String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            auth.currentUser?.sendEmailVerification()
            addInfoToDatabase(name)
        }

    }


    private fun addInfoToDatabase(name: String){
        val email = auth.currentUser?.email
        if(email != null){
            val user = userBase.child(email)
            user.child("email").setValue(email)
            user.child("name").setValue(name)
            user.child("readChapters").setValue(0)
            user.child("level").setValue("новачок")
            user.child("comments")
            user.child("team")
        }else{
            throw Exception("user email is null")
        }
    }


    // TODO: клас який буде це викликати має містити наступний код:
    //  getUserInfo("a"){ data ->
    //                if (data != null){
    //                   не нуль, обробляєм
    //                }else{
    //                    нуль, кажемо це користувачу
    //                }
    //            }
    fun getUserInfo(email: String, callback: (User?) -> Unit){
        userBase.orderByChild("email").startAt(email).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = snapshot.getValue(User::class.java)
                callback(result)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }


    // TODO: потрібно дати можливість міняти нік, кількість прочитаних глав,
    //  рівень та id команди в які може бути учасник. Потім це буде перевірятись.
    //  В хеш таблицю в String пишемо наприклад "name", а Any буде містити нове значення.
    fun updateInfoUser(hashMap: HashMap<String, Any>){
        val email = auth.currentUser?.email ?: throw Exception("user email is not found")
        userBase.child(email).updateChildren(hashMap)
    }


    fun logIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
        }else{
            throw Exception("user is not created or not verification")
        }
    }


    fun getStatusLogin() : Boolean {
        return auth.currentUser != null && auth.currentUser?.isEmailVerified!!
    }

    fun resetPassword(email: String){
        auth.sendPasswordResetEmail(email)
    }


}