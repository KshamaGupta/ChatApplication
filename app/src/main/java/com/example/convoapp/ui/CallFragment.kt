package com.example.convoapp.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.convoapp.MainActivity
import com.example.convoapp.R
import com.example.convoapp.activity.NumberActivity
import com.example.convoapp.activity.OtpActivity
import com.example.convoapp.databinding.FragmentCallBinding
import com.example.convoapp.databinding.FragmentChatBinding
import com.example.convoapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.*


class CallFragment : Fragment() {

    lateinit var binding: FragmentCallBinding
    lateinit var selectedImg:Uri
    lateinit var storage: FirebaseStorage
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    private lateinit var userName:String
    private lateinit var yes:Button
    private lateinit var no:Button

    private lateinit var profilePic:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCallBinding.inflate(layoutInflater)

        database= FirebaseDatabase.getInstance()
        storage= FirebaseStorage.getInstance()
        auth=FirebaseAuth.getInstance()

        binding.button3.setOnClickListener {
            val dialog=Dialog(requireContext(),R.style.dialog)
            dialog.setContentView(R.layout.dialog_layout)
            yes=dialog.findViewById(R.id.yesbtn)
            no=dialog.findViewById(R.id.nobtn)
            yes.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val i=Intent(requireContext(),NumberActivity::class.java)
                startActivity(i)
            }
            no.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        binding.circle.setOnClickListener {
            val intent = Intent()
            intent.action=Intent.ACTION_GET_CONTENT
            intent.type="image/+"
            startActivityForResult(intent,1)
        }
        //yaha tak
        binding.logout.setOnClickListener {
            uploadData()
        }
        // Inflate the layout for this fragment
    return binding.root
    }
    fun uploadData(){
        val reference=storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener{
            if(it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener { task->
                    uploadInfo(task.toString())
                }
            }
        }
    }
    fun uploadInfo(imgUrl:String){
        val user= UserModel(auth.uid.toString(),binding.text3.text.toString(),auth.currentUser!!.phoneNumber.toString(),imgUrl)

        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Updated Succesfully",Toast.LENGTH_SHORT).show()
                //startActivity(Intent(requireContext()
                   // ,MainActivity::class.java))

            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(data.data!=null){
                selectedImg=data.data!!
                binding.circle.setImageURI(selectedImg)
            }
        }
    }
}