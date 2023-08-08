package com.example.passwordlocker

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    // Stworzenie zmiennych
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonReg: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //Inicjalizacja zmiennych
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.registerButton)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)

//        Utworzenie Listener, który włącza się przy kliknięciu i sprawdza
//         czy dane są puste czy nie
        buttonReg.setOnClickListener {
            fun onClick(view: View) {
                var email = editTextEmail.text.toString()
                var password = editTextPassword.text.toString()

                //Ustawienie progressBara w trakcie
                progressBar.visibility = View.VISIBLE

                //Sprawdzenie czy email albo hasło są puste
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    showToast("Login i hasło nie mogą być puste!")
                    return
                }

                //Skrypt z strony firebase służący do tworzenia użytkownikó
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            showToast("Rejestracja powiodła się!")
                        } else {
                            showToast("Rejestracja nie powiodła się!")
                        }
                }
            }
        }
    }

    //Sprawdzenie czy użytkownik jest już aktualnei zalogowany
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // TODO sprawdzenie funckji reload(), żeby przeładować stronę kiedy żytkownik będzie zalogowany
        }
    }

    //Funkcja do wyświetlania powiadomień typu Toast dla użyytkownika
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}