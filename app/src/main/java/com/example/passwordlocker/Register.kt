package com.example.passwordlocker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordlocker.implementation.functionServiceImpl
import com.example.passwordlocker.interfaces.functionService
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Register() : AppCompatActivity() {

    companion object{
        val functionService: functionService = functionServiceImpl()
    }

    // Stworzenie zmiennych
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextSurName: TextInputEditText
    private lateinit var buttonReg: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var loginNow: TextView
    private lateinit var editTextPin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //Inicjalizacja zmiennych
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        editTextName = findViewById(R.id.name)
        editTextSurName = findViewById(R.id.surname)
        buttonReg = findViewById(R.id.registerButton)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        loginNow = findViewById(R.id.loginNow)
        editTextPin = findViewById(R.id.pin)

        //Przejście do rejestracji po naciśnięciu textView zaloguj się
        loginNow.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java )
            startActivity(intent)
            finish()
        }

        //Utworzenie Listener, który włącza się przy kliknięciu i sprawdza
        //czy dane są puste czy nie
        buttonReg.setOnClickListener {
                val email = editTextEmail.editableText.toString()
                val password = editTextPassword.editableText.toString()
                val name = editTextName.editableText.toString()
                val surname = editTextSurName.editableText.toString()
                val pin = editTextPin.editableText.toString()

                //Ustawienie progressBara w trakcie
                progressBar.visibility = View.VISIBLE

                //Sprawdzenie czy email albo hasło są puste
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) ||
                        TextUtils.isEmpty(pin)) {
                    functionService.showToast(this,"Wszystkie pola muszą być uzupełnione")
                    progressBar.visibility = View.GONE
                    return@setOnClickListener
                }

                //Skrypt z strony firebase służący do tworzenia użytkownikó
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            functionService.showToast(this,"Rejestracja powiodła się!")
                            val intent = Intent(applicationContext, Login::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            functionService.showToast(this,"Rejestracja nie powiodła się!")
                        }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //Przejscię do main activity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}