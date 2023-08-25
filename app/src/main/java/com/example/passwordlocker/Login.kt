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
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login() : AppCompatActivity() {
    companion object{
        val functionService: FunctionServiceImpl = FunctionServiceImpl()
    }

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var registerNow: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.buttonLogin)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        registerNow = findViewById(R.id.registerNow)


        //Przejście do logowania po naciśnięciu textView Zarejestruj sie
        registerNow.setOnClickListener{
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogin.setOnClickListener{
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

                //Logowanie się do firebase
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            functionService.showToast(this, "Logowanie powiodło się!")
                            //Przejscię do main activity
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            val user = auth.currentUser
                        } else {
                            functionService.showToast(this,"Logowanie nie powiodło się!")
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