package com.example.passwordlocker

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordlocker.implementation.FunctionServiceImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    companion object{
        val functionService: FunctionServiceImpl = FunctionServiceImpl()
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var addPassword: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        addPassword = findViewById(R.id.addPasswordButton)

        //Po naciśnięciu guzika przechodzimy do passwordDetails
        addPassword.setOnClickListener {
            startActivity(functionService.createIntent(applicationContext,PasswordDetails::class.java))
        }
    }

    //Wylogowywanie z aplikacji podczas wyłączenia i wznowwienia
    override fun onDestroy() {
        super.onDestroy()
        PasswordDetails.functionService.signOut()
        startActivity(functionService.createIntent(
            applicationContext,
            Login::class.java)
        )
    }
    override fun onRestart() {
        super.onRestart()
        PasswordDetails.functionService.signOut()
        startActivity(functionService.createIntent(
            applicationContext,
            Login::class.java)
        )
    }
}