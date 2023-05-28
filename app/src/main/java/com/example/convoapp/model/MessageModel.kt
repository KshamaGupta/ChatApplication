package com.example.convoapp.model

data class MessageModel(
    var message:String="",
    var senderId:String="",
    var timestamp: Long?=0
)
