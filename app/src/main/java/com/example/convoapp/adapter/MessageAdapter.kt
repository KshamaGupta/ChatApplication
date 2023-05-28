package com.example.convoapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convoapp.R
import com.example.convoapp.databinding.ReceiverItemLayoutBinding
import com.example.convoapp.databinding.SentItemLayoutBinding
import com.example.convoapp.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date

class MessageAdapter(var context: Context,var list: ArrayList<MessageModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }
    var ITEM_SENT=1
    var ITEM_RECEIVE=2

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().uid==list[position].senderId) ITEM_SENT else ITEM_RECEIVE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val message=list[position]


        if(holder.itemViewType==ITEM_SENT){
            val viewHolder=holder as SentViewHolder
            viewHolder.binding.senderText.text=message.message
            val date=message.timestamp
            val simpleDateFormat = SimpleDateFormat("hh-mm a ")
            val dateTime = simpleDateFormat.format(date)
            viewHolder.binding.senderTime.text=dateTime.toString()

        }
        else{
            val viewHolder =holder as ReceiverViewHolder
            viewHolder.binding.receiverText.text=message.message
            val date=message.timestamp
            val simpleDateFormat = SimpleDateFormat("hh-mm a ")
            val dateTime = simpleDateFormat.format(date)
            viewHolder.binding.receiverTime.text=dateTime.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==ITEM_SENT)
            return SentViewHolder(LayoutInflater.from(context).inflate(R.layout.sent_item_layout,parent,false))
        else {
            return ReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.receiver_item_layout,parent,false)
            )
        }
    }
    inner class SentViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding=SentItemLayoutBinding.bind(view)
    }
    inner class ReceiverViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding=ReceiverItemLayoutBinding.bind(view)
    }
}