package com.example.hans_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.hans_manager.CGet.GetKindStr
import kotlinx.android.synthetic.main.activity_add_menu.*
import okhttp3.*
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject

class ActivityAddMenu : AppCompatActivity() {

    var m_menu_kind = ""
    var m_menu_name = ""
    var m_menu_desc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)


        et_menu_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                Log.d("로그", p0.toString())
                m_menu_name = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        et_menu_desc.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                Log.d("로그", p0.toString())
                m_menu_desc = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        radio_group.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rb_normal -> m_menu_kind ="1"
                R.id.rb_premium -> m_menu_kind ="2"
                R.id.rb_bbq -> m_menu_kind ="3"
                R.id.rb_dessert -> m_menu_kind ="4"
                R.id.rb_lunchbox -> m_menu_kind ="5"
                R.id.rb_party -> m_menu_kind ="6"
            }
        }

        btn_add_menu.setOnClickListener {
            InsertMenu(it)
        }

        btn_cancel_menu.setOnClickListener {
            finish()
        }


    }

    fun InsertMenu(v : View)
    {
        val url = CListCollector.AWS_DOMAIN +"/hans/insert_menu"
        val client : OkHttpClient = OkHttpClient()

        val body: RequestBody = FormBody.Builder().add("kind",m_menu_kind).add("name",m_menu_name).add("desc",m_menu_desc).build()
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
                    getMenuID()
                }

            }
        })
    }

    fun getMenuID()
    {
        var id : String
        val url = CListCollector.AWS_DOMAIN+"/hans/select_menu_id"
        val client : OkHttpClient = OkHttpClient()
        val body: RequestBody = FormBody.Builder()
            .add("kind", m_menu_kind)
            .add("name", m_menu_name)
            .build()

        val request = Request.Builder().url(url).post(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //에러 메세지 출력
                Log.d("로그", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val str_response1 = response.body!!.string()
                var json_array: JSONArray = JSONArray(str_response1)

                var json_objdetail: JSONObject = json_array.getJSONObject(0)
                id = json_objdetail.getString("id")

                //Update Main UI
                runOnUiThread {
                    var tmpMenuInfo  = D_PMenu(id, m_menu_kind, GetKindStr(m_menu_kind.toInt()), m_menu_name, m_menu_desc )
                    CListCollector.list_food_menu.add(tmpMenuInfo)
                    Toast.makeText(applicationContext, "메뉴가 추가 되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()

                }
            }
        })

    }

}