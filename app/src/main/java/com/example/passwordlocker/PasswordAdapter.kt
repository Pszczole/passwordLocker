package com.example.passwordlocker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordlocker.implementation.FunctionServiceImpl
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PasswordAdapter(options: FirestoreRecyclerOptions<Password>, private val context: Context) :
FirestoreRecyclerAdapter<Password, PasswordAdapter.PasswordViewHolder>(options){

    companion object{
        val functionService: FunctionServiceImpl = FunctionServiceImpl()
    }

    class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          var itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
          var itemPassword: TextView = itemView.findViewById(R.id.itemPassword)
          var itemDate: TextView = itemView.findViewById(R.id.itemDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.recycler_password_item, parent ,false)
        return PasswordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int, password: Password) {
        holder.itemTitle.setText(password.title)
        holder.itemPassword.setText(password.password)
        holder.itemDate.setText(functionService.timestampToString(password.timestamp))
    }
}