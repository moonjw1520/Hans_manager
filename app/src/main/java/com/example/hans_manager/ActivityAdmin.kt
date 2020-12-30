package com.example.hans_manager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_admin.*

class ActivityAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        btn_add_employee.setOnClickListener {
            val intent = Intent(this,ActivityAddEmployee::class.java)
            startActivity(intent)
        }

        btn_del_employee.setOnClickListener {

            val nextIntent = Intent(this, Activity_list_delivery_man::class.java)

            nextIntent.putExtra("id", "0")
            nextIntent.putExtra("event_date", "0")
            nextIntent.putExtra("flag", 1)
            startActivity(nextIntent)
        }

        btn_add_car.setOnClickListener {
            val intent = Intent(this, ActivityAddCar::class.java)
            startActivity(intent)
        }

        btn_del_car.setOnClickListener {
            val nextIntent = Intent(this, Activity_list_cars_info::class.java)
            nextIntent.putExtra("id", "0")
            nextIntent.putExtra("flag", 1)
            startActivity(nextIntent)
        }

        btn_add_package.setOnClickListener {
            val intent = Intent(this, ActivityAddMenu::class.java)
            startActivity(intent)
        }

        btn_del_package.setOnClickListener {

            val nextIntent = Intent(this, Activity_list_food_menu::class.java)
            nextIntent.putExtra("id", "0")
            nextIntent.putExtra("event_date", "0")
            nextIntent.putExtra("flag", 1)

            startActivity(nextIntent)
        }

    }
}