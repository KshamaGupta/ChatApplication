package com.example.convoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.convoapp.activity.NumberActivity
import com.example.convoapp.adapter.viewPagerAdapter
import com.example.convoapp.databinding.ActivityMainBinding
import com.example.convoapp.ui.CallFragment
import com.example.convoapp.ui.ChatFragment
import com.example.convoapp.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater=menuInflater
        inflater.inflate(R.menu.menu_item,menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting->{
                Toast.makeText(this,"Setting is clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.groupChat->{
                Toast.makeText(this,"Group chat is clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.profile->{
                Toast.makeText(this,"Profile is clicked",Toast.LENGTH_SHORT).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val fragmentArrayList=ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())

        auth=FirebaseAuth.getInstance()
        if(auth.currentUser==null){
            startActivity(Intent(this,NumberActivity::class.java))
            finish()
        }
        val adapter=viewPagerAdapter(this,supportFragmentManager,fragmentArrayList)
        binding!!.viewPager.adapter=adapter
        binding!!.tabs.setupWithViewPager(binding!!.viewPager)

    }


}