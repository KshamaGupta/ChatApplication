package com.example.convoapp.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.convoapp.MainActivity
import com.example.convoapp.R
import com.example.convoapp.adapter.viewPagerAdapter
import com.example.convoapp.databinding.FragmentCallBinding
import com.example.convoapp.databinding.FragmentStatusBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class StatusFragment : Fragment() {
    lateinit var binding: FragmentStatusBinding
    lateinit var phoneNo: EditText
    lateinit var callbtn: FloatingActionButton
    val PERMISSION_CODE:Int=100



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStatusBinding.inflate(layoutInflater)
        phoneNo=binding.editTextPhone3
        callbtn=binding.callbtn
        checkPermission()
        callbtn.setOnClickListener {
            val phone=phoneNo.text.toString()
            if(phone.isNotEmpty()){
                val callIntent=Intent(Intent.ACTION_CALL)
                callIntent.data= Uri.parse("tel: $phone")
                startActivity(callIntent)
            }
        }
        return binding.root
    }
    private fun checkPermission(){
        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),101)
        }
    }


}