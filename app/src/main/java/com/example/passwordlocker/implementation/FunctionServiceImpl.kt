package com.example.passwordlocker.implementation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.passwordlocker.interfaces.FunctionService
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import org.mindrot.jbcrypt.BCrypt
import java.text.SimpleDateFormat
import java.util.*

open class FunctionServiceImpl: FunctionService {

    //Funkcja do wyświetlania powiadomień typu Toast dla użyytkownika
    override fun showToast(context: Context,message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // Funkcja do wylogowywania się z Firebase
    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    //Validacja hasła używana podczas haszowania
    override fun passwordValidation(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }

    //Tworzenie intentów
    override fun createIntent(context: Context, kotlinActivity: Class<*>): Intent {
        return Intent(context, kotlinActivity)
    }

    //Metoda odwołuje się do kolekcji haseł podanej dla unikatowego użytkownika
    //Z kolekcji użytkowników wybieramy konkretnego usera, i następnie wybieramy jego hasła
    @SuppressLint("SuspiciousIndentation")
    override fun getCollectionReference(): CollectionReference {
       val currentUser = FirebaseAuth.getInstance().currentUser
           return FirebaseFirestore.getInstance().collection("users")
                .document(currentUser!!.uid)
                .collection("usersPasswords")
    }

    //Konwertowanie daty w stringa, służy do zapisu do bazy danych
    override fun timestampToString(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(date)
    }
}