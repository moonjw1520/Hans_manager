package com.example.hans_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.hans_manager.CListCollector.AWS_DOMAIN
import com.example.hans_manager.CListCollector.TAG
import com.example.hans_manager.CListCollector.list_event
import com.example.hans_manager.CListCollector.list_food_menu
import kotlinx.android.synthetic.main.activity_list_delivery_man.*
import kotlinx.android.synthetic.main.activity_list_delivery_man.btn_Cancel
import kotlinx.android.synthetic.main.activity_list_delivery_man.btn_Ok
import kotlinx.android.synthetic.main.activity_list_delivery_man.listView
import kotlinx.android.synthetic.main.activity_list_food_menu.*
import okhttp3.*
import okio.IOException

class Activity_list_food_menu : AppCompatActivity(), CheckInterface
{

    lateinit var m_event_id : String
    lateinit var m_event_date : String
    var m_nEvent_Index : Int = 0
    var m_nflag : Int = 0
    private lateinit var m_Adapter : CAdapter_list_food_menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_food_menu)


        m_event_id = intent.getStringExtra("id")
        m_event_date = intent.getStringExtra("event_date")
        m_nEvent_Index = intent.getIntExtra("event_index",0)

        m_nflag = intent.getIntExtra("flag",0)
        var m_list : ArrayList<D_SMenu> =  ArrayList()

        btn_Ok.setOnClickListener {
            list_event[m_nEvent_Index].List_Menu.clear()

            val checkedItem = listView.checkedItemPositions

            (0 until  m_Adapter.count)
                .filter { checkedItem.get(it) }
                .forEach {
                    m_list.add(D_SMenu(
                        CListCollector.list_food_menu[it].id,
                        CListCollector.list_food_menu[it].kind,
                        CListCollector.list_food_menu[it].kind_str,
                        CListCollector.list_food_menu[it].name ))
                    if(m_nflag == 0)
                    {
                        list_event[m_nEvent_Index].List_Menu.add(D_SMenu(
                            CListCollector.list_food_menu[it].id,
                            CListCollector.list_food_menu[it].kind,
                            CListCollector.list_food_menu[it].kind_str,
                            CListCollector.list_food_menu[it].name ) )
                    }
                }

            if(m_nflag == 0)
            {
                DeleteMatching()
            }
            else
            {
                for( i in 0 until m_list.size)
                {
                    for(inx in 0 until list_food_menu.size)
                    {
                        if(m_list[i].id == list_food_menu[inx].id)
                        {
                            list_food_menu.removeAt(inx)
                        }
                    }
                }


                //이벤트 전부 확인해서 해당 패키지를 사용하고 있으면 지운다.
                for( i in 0 until  m_list.size)
                {
                    for(inx in 0 until  list_event.size)
                    {
                        for(index in 0 until  list_event[inx].List_Menu.size)
                        {
                            if(m_list[i].id == list_event[inx].List_Menu[index].id)
                            {
                                list_event[inx].List_Menu.removeAt(index)
                            }
                        }
                    }
                }

                for(i in 0 until  m_list.size)
                {
                    DeleteMenu(m_list[i].id)
                }

            }
            finish()
        }


        this.btn_Cancel.setOnClickListener {
            finish()
        }

        m_Adapter =  CAdapter_list_food_menu(this@Activity_list_food_menu, CListCollector.list_food_menu, this@Activity_list_food_menu)
        listView.adapter = m_Adapter
        //리스트뷰의 선택모드를 다중선택모드로 설정
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }



    fun InsertMatching(a_menu_id: String) {
        val url = "$AWS_DOMAIN/hans/matching_menu"
        val client : OkHttpClient = OkHttpClient()

        val body: RequestBody = FormBody.Builder().add("event_id",m_event_id).add("menu_id",a_menu_id).build()
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

    //-------------------matching DB delete--------------------------
    fun DeleteMatching() {
        val url = "$AWS_DOMAIN/hans/matching_menu_delete"
        val client: OkHttpClient = OkHttpClient()
        Log.d("로그", "Delete event_id: ${m_event_id}")
        val body: RequestBody = FormBody.Builder().add("event_id", m_event_id).build()
        val request = Request.Builder().url(url).post(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //에러 메세지 출력
                Log.d("로그", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("로그", "${response.body.toString()}")
                //Update Main UI
                Log.d("로그","DeleteMatching.....")
                runOnUiThread {

                    for(i in 0 until  list_event[m_nEvent_Index].List_Menu.size)
                    {
                        Log.d("로그","insert name :${list_event[m_nEvent_Index].List_Menu[i].name}")
                        InsertMatching(list_event[m_nEvent_Index].List_Menu[i].id)
                    }
                }
            }
        })
    }

    override fun onCheckClicked(position: Int)
    {
        Log.d(TAG, "Activity onCheckClicked")

        var title =   "${list_food_menu[position].kind_str}" + " ${list_food_menu[position].name} \n"

        var SendMsg = ""

        var tmpStr = list_food_menu[position].desc.split(",")

        for (inx in 0 until tmpStr.size) {
            SendMsg = SendMsg + tmpStr[inx] + "\n"
        }
        SendMsg += "\n"

        txt_menu_detail1.text = title
        txt_menu_detail2.text = SendMsg
    }

    fun DeleteMenu(a_id : String) {
        val url = "${CListCollector.AWS_DOMAIN}/hans/delete_menu"
        val client: OkHttpClient = OkHttpClient()
        Log.d("로그", "Delete car_id: ${a_id}")
        val body: RequestBody = FormBody.Builder().add("id", a_id).build()
        val request = Request.Builder().url(url).post(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //에러 메세지 출력
                Log.d("로그", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("로그", "${response.body.toString()}")
                //Update Main UI
                Log.d("로그","DeleteMatching.....")
                runOnUiThread {
                    Toast.makeText(applicationContext, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }



}