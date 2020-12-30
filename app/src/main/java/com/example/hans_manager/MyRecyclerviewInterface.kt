package com.example.hans_manager

// 커스텀 인터페이스
interface MyRecyclerviewInterface
{
    fun onItemClicked(position : Int, a_evetData : CEvent)
}

interface StateRecyclerviewInterface
{
    fun onStateEventClicked(position : Int, aEventData : String)
}

interface CheckInterface
{
    fun onCheckClicked(position : Int)
}