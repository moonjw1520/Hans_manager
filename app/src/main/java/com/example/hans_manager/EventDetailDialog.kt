package com.example.hans_manager

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log

class EventDetailDialog(context: Context): Dialog(context){
    val TAG: String = "로그"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_event_detail)
        //배경 투명
        //window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Log.d(TAG,"MainActivity - onCreate() called")

    }
}