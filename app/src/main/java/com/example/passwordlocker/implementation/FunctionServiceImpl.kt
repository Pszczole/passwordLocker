package com.example.passwordlocker.implementation

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.widget.Toast
import com.example.passwordlocker.Password
import com.example.passwordlocker.PasswordDetails
import com.example.passwordlocker.interfaces.FunctionService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.mindrot.jbcrypt.BCrypt

open class FunctionServiceImpl: FunctionService {
    override fun showToast(context: Context,message: String) {
        //Funkcja do wyświetlania powiadomień typu Toast dla użyytkownika
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // Funkcja do wylogowywania
    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    //Check with debugger
    override fun passwordValidation(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }

    //Tworzenie intentów
    override fun createIntent(context: Context, kotlinActivity: Class<*>): Intent {
        return Intent(context, kotlinActivity)
    }

    //Metoda odwołuje się do kolkecji haseł podanej dla unikatowego użytkownika
    //Z kolekcji użytkowników wybieramy konkretnego usera, i następnie wybieramy jego hasła
    @SuppressLint("SuspiciousIndentation")
    override fun getCollectionReference(): CollectionReference {
       val currentUser = FirebaseAuth.getInstance().currentUser
           return FirebaseFirestore.getInstance().collection("users")
                .document(currentUser!!.uid)
                .collection("usersPasswords")
    }


}