package com.example.convoapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.convoapp.MainActivity
import com.example.convoapp.R
import com.example.convoapp.adapter.MessageAdapter
import com.example.convoapp.databinding.ActivityChatBinding
import com.example.convoapp.model.MessageModel
import com.example.convoapp.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.Date


class ChatActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var senderUid:String
    private lateinit var reciverUid:String
    private lateinit var senderRoom:String
    private lateinit var receiverRoom:String
    private lateinit var userName:String

    private lateinit var profilePic:String
    private lateinit var list:ArrayList<MessageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= FirebaseDatabase.getInstance()
        list= ArrayList()

        senderUid=FirebaseAuth.getInstance().uid.toString()
        reciverUid=intent.getStringExtra("uid")!!
        userName=intent.getStringExtra("userName")!!
        profilePic=intent.getStringExtra("profilePic")!!
        binding.userName.setText(userName)
        Picasso.get().load(profilePic).placeholder(R.drawable.baseline_person_pin_24).into(binding.profileImage)
        binding.backArrow.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        senderRoom=senderUid+reciverUid
        receiverRoom=reciverUid+senderUid
        binding.send.setOnClickListener{
            if(binding.enterMessage.text.isEmpty()){
                Toast.makeText(this,"Please Enter messages",Toast.LENGTH_SHORT).show()
            }
            else{
                val message=MessageModel(binding.enterMessage.text.toString(),senderUid,Date().time)
                val randomkey=database.reference.push().key
                database.reference.child("chats")
                    .child(senderRoom).child("message").child(randomkey!!)
                    .setValue(message).addOnSuccessListener {
                        database.reference.child("chats").child(receiverRoom).child("message")
                            .child(randomkey!!).setValue(message).addOnSuccessListener {
                                binding.enterMessage.text=null
                                Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show()
                            }
                    }
            }
        }

        database.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object :ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity,"Error:$error",Toast.LENGTH_SHORT).show()
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (snapshot1 in snapshot.children){
                        val data=snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }
                    binding.chatRecyclerView.adapter=MessageAdapter(this@ChatActivity,list)

                }
            })
        binding.imageView4.setOnClickListener {
            val frag=StatusFragment()
            val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(com.google.android.material.R.id.container,frag)
            transaction.commit()
        }
    }
}