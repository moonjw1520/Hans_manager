package com.example.hans_manager

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ActivityCreateEvent: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tool_bar)
    }



    fun showCreateEvent(v: View)
    {
        Log.d("로그","showCreateEvent..")
    }


}