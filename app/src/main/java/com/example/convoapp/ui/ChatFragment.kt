package com.example.convoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.convoapp.R
import com.example.convoapp.adapter.ChatAdapter
import com.example.convoapp.databinding.FragmentChatBinding
import com.example.convoapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
    lateinit var binding:FragmentChatBinding
    private var database:FirebaseDatabase?=null
    lateinit var userList:ArrayList<UserModel>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentChatBinding.inflate(layoutInflater)
        database= FirebaseDatabase.getInstance()
        userList= ArrayList()
        database!!.reference.child("users")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for(snapshot1 in snapshot.children){
                        val user=snapshot1.getValue(UserModel::class.java)
                        if(user!!.uid!=FirebaseAuth.getInstance().uid){
                            userList.add(user)
                        }
                    }
                    binding.userRecycler.adapter=ChatAdapter(requireContext(),userList)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })



        // Inflate the layout for this fragment
        return binding.root

    }


}