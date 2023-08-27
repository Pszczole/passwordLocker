package com.example.passwordlocker.interfaces

import android.content.Context
import android.content.Intent
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference

interface FunctionService {
      fun showToast(context: Context,message: String)
      fun signOut()
      fun passwordValidation(password: String, hashedPassword: String): Boolean
      fun createIntent(context: Context, kotlinActivity: Class<*>): Intent
      fun getCollectionReference(): CollectionReference
      fun timestampToString(timestamp: Timestamp): String
}