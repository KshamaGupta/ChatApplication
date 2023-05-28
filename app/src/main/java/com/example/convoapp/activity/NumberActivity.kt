package com.example.convoapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.convoapp.MainActivity
import com.example.convoapp.R
import com.example.convoapp.databinding.ActivityNumberBinding
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNumberBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding.button.setOnClickListener{
            if(binding.editTextPhone.text!!.isEmpty()){
                Toast.makeText(this,"Please Enter Number",Toast.LENGTH_SHORT).show()
            }
            else{
                var intent=Intent(this,OtpActivity::class.java)
                intent.putExtra("number",binding.editTextPhone.text!!.toString())
                startActivity(intent)
            }
        }
    }
}