package com.example.passwordlocker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import com.example.passwordlocker.implementation.FunctionServiceImpl
import org.mindrot.jbcrypt.BCrypt;
class PasswordDetails : AppCompatActivity() {

    companion object{
        val functionService: FunctionServiceImpl = FunctionServiceImpl()
    }

    private lateinit var title: EditText
    private lateinit var password: EditText
    private lateinit var savePassword: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_details)
        title = findViewById(R.id.passwordTitle)
        password = findViewById(R.id.passwordDetail)
        savePassword = findViewById(R.id.savePasswordButton)

        savePassword.setOnClickListener {
            savePassword()
        }
    }

    private fun savePassword(){
        val title = title.editableText.toString()
        val password = password.editableText.toString()
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(password)){
            functionService.showToast(this,"Wszystkie pola muszą być uzupełnione")
            return
        }
        //Hashed password
        BCrypt.hashpw(password, BCrypt.gensalt())
    }

    //Wylogowywanie z aplikacji podczas wyłączenia i wznowwienia
    override fun onDestroy() {
        super.onDestroy()
        functionService.signOut()
        startActivity(MainActivity.functionService.createIntent(
            applicationContext,
            Login::class.java)
        )
    }
    override fun onRestart() {
        super.onRestart()
        functionService.signOut()
        startActivity(MainActivity.functionService.createIntent(
            applicationContext,
            Login::class.java)
        )
    }
}