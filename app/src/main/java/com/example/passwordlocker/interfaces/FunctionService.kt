package com.example.passwordlocker.interfaces

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent

interface FunctionService {
      fun showToast(context: Context,message: String)
      fun signOut()
      fun passwordValidation(password: String, hashedPassword: String): Boolean
      fun createIntent(context: Context, kotlinActivity: Class<*>): Intent
}