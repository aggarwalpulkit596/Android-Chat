package com.android.chatapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_chat)

    button2.setOnClickListener {
      val msg = editText3.text.toString()
      val email = FirebaseAuth.getInstance().currentUser!!.email

      FirebaseDatabase.getInstance().reference.child("messages")
        .push()
        .setValue(
          Messsage(email!!,msg)
        )
    }


    val query = FirebaseDatabase.getInstance().reference.child("messages")

    val options = FirebaseListOptions.Builder<Messsage>()
      .setLayout(android.R.layout.simple_list_item_1)
      .setQuery(query,Messsage::class.java)
      .build()

    val adapter = object : FirebaseListAdapter<Messsage>(options){
      override fun populateView(v: View, model: Messsage, position: Int) {
        (v as TextView).text = model.email + "\n" + model.msg
      }
    }
    adapter.startListening()

    messages.adapter = adapter

  }
}

class Messsage(var email: String, var msg: String) {
  constructor():this("","")
}
