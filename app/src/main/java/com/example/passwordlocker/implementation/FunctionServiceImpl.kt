package com.example.passwordlocker.implementation

import android.content.Context
import android.widget.Toast
import com.example.passwordlocker.interfaces.FunctionService
import com.google.firebase.auth.FirebaseAuth
import org.mindrot.jbcrypt.BCrypt

open class FunctionServiceImpl: FunctionService {
    override fun showToast(context: Context,message: String) {
        //Funkcja do wyświetlania powiadomień typu Toast dla użyytkownika
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
    //Check with debugger
    override fun passwordValidation(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }


}