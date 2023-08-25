package com.example.passwordlocker.interfaces

import android.content.Context

interface FunctionService {
      fun showToast(context: Context,message: String)
      fun signOut()
      fun passwordValidation(password: String, hashedPassword: String): Boolean
}