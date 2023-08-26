package com.example.passwordlocker

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PasswordAdapter(options: FirestoreRecyclerOptions<Password>, context: Context) :
FirestoreRecyclerAdapter<Password, PasswordAdapter.PasswordViewHolder>(options){

    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          var itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
          var itemPassword: TextView = itemView.findViewById(R.id.itemPassword)
          var itemDate: TextView = itemView.findViewById(R.id.itemDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        TODO("Not yet implemented")
        //1:11:11
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int, model: Password) {
        TODO("Not yet implemented")
    }
}