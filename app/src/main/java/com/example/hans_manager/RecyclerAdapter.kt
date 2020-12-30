package com.example.hans_manager

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.lang.NullPointerException
import java.lang.NumberFormatException

class RecyclerAdapter(val modelist:ArrayList<CEvent>, a_myRecyclerviewInterface : MyRecyclerviewInterface) :
                                                                RecyclerView.Adapter<BranchUserViewHolder>()
{

    val TAG: String = "로그"
    private  var m_modelList = ArrayList<CEvent>()
    private  var m_myRecyclerviewInterface : MyRecyclerviewInterface? = null
    init {
        this.m_myRecyclerviewInterface = a_myRecyclerviewInterface
         m_modelList  = modelist
    }


    //뷰홀더가 생성 되었을때
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchUserViewHolder {
        return BranchUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_user,parent,false),this.m_myRecyclerviewInterface!!)
    }

    //목록의 아이템 개수
    override fun getItemCount(): Int {
        return m_modelList.size
    }

    //뷰와 뷰홀더가 묶였을때
    override fun onBindViewHolder(holder: BranchUserViewHolder, position: Int) {
        Log.d(TAG,"RecyclerAdapter - onBindViewHolder() called / position : $position")
        holder.bind(this.m_modelList[position])
    }

}