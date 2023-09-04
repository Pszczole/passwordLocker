package com.example.passwordlocker

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordlocker.implementation.FunctionServiceImpl
import com.example.passwordlocker.interfaces.FunctionService
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
class Register() : AppCompatActivity() {

    companion object{
        val functionService: FunctionService = FunctionServiceImpl()
    }

    // Stworzenie zmiennych
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonReg: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var loginNow: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //Inicjalizacja zmiennych
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.registerButton)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        loginNow = findViewById(R.id.loginNow)

        //Przejście do rejestracji po naciśnięciu textView zaloguj się
        loginNow.setOnClickListener{
            startActivity(MainActivity.functionService.createIntent(
                applicationContext,
                Login::class.java)
            )
            finish()
        }

        //Utworzenie Listener, który włącza się przy kliknięciu i sprawdza
        //czy dane są puste czy nie
        buttonReg.setOnClickListener {
                val email = editTextEmail.editableText.toString()
                val password = editTextPassword.editableText.toString()

                //Ustawienie progressBara w trakcie
                progressBar.visibility = View.VISIBLE

                //Sprawdzenie czy email albo hasło są puste
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    functionService.showToast(this,"Wszystkie pola muszą być uzupełnione")
                    progressBar.visibility = View.GONE
                    return@setOnClickListener
                }

                //Skrypt z strony firebase służący do tworzenia użytkowników
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            functionService.showToast(this,"Rejestracja powiodła się!")
                            // Po rejestracji od razu użytkownik jest zalogowany dlatego
                            //przekierowuje go do MainActivity ze względu na metod onStart w Login
                            startActivity(MainActivity.functionService.createIntent(
                                applicationContext,
                                Login::class.java)
                            )
                            finish()
                        } else {
                            functionService.showToast(this,"Rejestracja nie powiodła się!")
                        }
                }
        }
    }

    //Kiedy użytkownik jest zalogowany to automatycznie przenosi go do
    // main activity
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //Przejscię do main activity
            startActivity(MainActivity.functionService.createIntent(
                applicationContext,
                MainActivity::class.java)
            )
            finish()
        }
    }

    //Dodać methody z cyklu życia aplikacji
}