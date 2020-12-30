package com.example.hans_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.hans_manager.CListCollector.AWS_DOMAIN
import com.example.hans_manager.CListCollector.list_cars_info
import com.example.hans_manager.CListCollector.list_delivery_man
import com.example.hans_manager.CListCollector.list_event
import kotlinx.android.synthetic.main.activity_list_delivery_man.*
import okhttp3.*
import okio.IOException

class Activity_list_cars_info : AppCompatActivity() {
    lateinit var m_event_id : String
    var m_nDelivery_index : Int = 0
    var m_nEvent_Index : Int = 0

    private lateinit var m_Adapter : CAdapter_list_cars_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cars_info)

        m_event_id = intent.getStringExtra("id")
        m_nDelivery_index = intent.getIntExtra("delivery_index",0)
        m_nEvent_Index = intent.getIntExtra("event_index",0)


        btn_Ok.setOnClickListener {

            var car_id ="0"
            var car_num ="미정"

            CListCollector.list_event[m_nEvent_Index].List_delivery_man[m_nDelivery_index].car_id =  car_id
            CListCollector.list_event[m_nEvent_Index].List_delivery_man[m_nDelivery_index].car_num = car_num

            val checkedItem = listView.checkedItemPositions

            (0 until m_Adapter.count)
                .filter { checkedItem.get(it) }
                .forEach {
                    car_id = list_cars_info[it].id
                    car_num = list_cars_info[it].num_str
                    CListCollector.list_event[m_nEvent_Index].List_delivery_man[m_nDelivery_index].car_id =  list_cars_info[it].id
                    CListCollector.list_event[m_nEvent_Index].List_delivery_man[m_nDelivery_index].car_num = list_cars_info[it].num_str
                }

            for(i in 0 until list_delivery_man.size)
            {
                if(list_event[m_nEvent_Index].List_delivery_man[m_nDelivery_index].id == list_delivery_man[i].id)
                {
                    for(inx in 0 until  list_delivery_man[i].List_Event.size)
                    {
                        if(list_event[m_nEvent_Index].id == list_delivery_man[i].List_Event[inx].id)
                        {
                            list_delivery_man[i].List_Event[inx].car_id = car_id
                            list_delivery_man[i].List_Event[inx].car_num = car_num
                        }
                    }
                }
            }



            UpdateMatchingCarsInfo()
            finish()
        }



        this.btn_Cancel.setOnClickListener {
            finish()
        }

        m_Adapter =  CAdapter_list_cars_info(this@Activity_list_cars_info, CListCollector.list_cars_info)
        listView.adapter = m_Adapter
        //리스트뷰의 선택모드를 다중선택모드로 설정
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
    }


    fun UpdateMatchingCarsInfo() {
        val url = "$AWS_DOMAIN/hans/update_matching_cars_info"
        val client : OkHttpClient = OkHttpClient()

        val body: RequestBody = FormBody.Builder().add("car_id",list_event[m_nEvent_Index].List_delivery_man[m_nDelivery_index].car_id).add("event_id",m_event_id).add("delivery_id",list_event[m_nEvent_Index].List_delivery_man[m_nDelivery_index].id).build()
        val request = Request.Builder().url(url).post(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //에러 메세지 출력
                Log.d("로그", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                Log.d("로그", "${response.body.toString()}")
                //Update Main UI
                runOnUiThread {
                    Log.d("로그","InsertMatching.....")
                    Toast.makeText( applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

}