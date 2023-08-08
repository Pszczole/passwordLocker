package com.example.passwordlocker.implementation

import android.content.Context
import android.widget.Toast
import com.example.passwordlocker.interfaces.functionService

open class functionServiceImpl: functionService {
    override fun showToast(context: Context,message: String) {
        //Funkcja do wyświetlania powiadomień typu Toast dla użyytkownika
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}