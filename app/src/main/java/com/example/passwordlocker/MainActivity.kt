package com.example.passwordlocker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordlocker.implementation.FunctionServiceImpl
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {
    companion object{
        val functionService: FunctionServiceImpl = FunctionServiceImpl()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var addPassword: FloatingActionButton
    private lateinit var passwordAdapter: PasswordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        addPassword = findViewById(R.id.addPasswordButton)
        recyclerView = findViewById(R.id.recyclerView)

        //Po naciśnięciu guzika przechodzimy do passwordDetails
        addPassword.setOnClickListener {
            startActivity(functionService.createIntent(
                applicationContext,
                PasswordDetails::class.java)
            )
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val query = functionService.getCollectionReference()
            .orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<Password>()
            .setQuery(query, Password::class.java)
            .build()
        recyclerView.layoutManager = LinearLayoutManager(this)
        passwordAdapter = PasswordAdapter(options,this)
        recyclerView.adapter = passwordAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //create a menu with menuInflater and R.menu.menu resource
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.popup_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Handle the selection of menu items
        return when(item.itemId){
            R.id.popupMenuItem -> {
                functionService.signOut()
                startActivity(functionService.createIntent(
                    this@MainActivity,
                    Login::class.java
                ))
                true
            }
            else -> {super.onOptionsItemSelected(item)}
        }
    }

    override fun onStart() {
        super.onStart()
        passwordAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        passwordAdapter.stopListening()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        passwordAdapter.notifyDataSetChanged()
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