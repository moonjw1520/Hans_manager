package com.example.hans_manager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.TextTemplate
import kotlinx.android.synthetic.main.recyclerview_select_user_main.*

class SendEventMsgView : AppCompatActivity() {

    val TAG = "로그"

    var m_modelList = ArrayList<CBranchInfoUser>()

    private lateinit var RecyclerAdapter:RecyclerAdapter


    val defaultText = TextTemplate(
        text = """
        카카오링크는 카카오 플랫폼 서비스의 대표 기능으로써 사용자의 모바일 기기에 설치된 카카오 플랫폼과 연동하여 다양한 기능을 실행할 수 있습니다.     
    """.trimIndent(),
        link = Link(
            webUrl = "https://developers.kakao.com",
            mobileWebUrl = "https://developers.kakao.com"
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_select_user_main)

        //m_modelList.add(CBranchInfoUser("saaa","bbbb", "ccc"))
        //callAdapter()
        TalkApiClient.instance.friends { friends, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡 친구 목록 가져오기 실패", error)
            }
            else {
                Log.d(TAG, "카카오톡 친구 목록 가져오기 성공 \n${friends!!.elements.joinToString("\n")}")

                if (friends.elements.isEmpty()) {
                    Log.e(TAG, "메시지 보낼 친구가 하나도 없어요 ㅠㅠ")
                }
                else {

                    // 서비스에 상황에 맞게 메시지 보낼 친구의 UUID를 가져오세요.
                    // 이 샘플에서는 친구 목록을 화면에 보여주고 체크박스로 선택된 친구들의 UUID 를 수집하도록 구현했습니다.
                    //FriendsActivity.startForResult(
//                   MainEventActivity.startForResult(
//                        this,
//                        friends.elements.map { PickerItem(it.uuid, it.profileNickname, it.profileThumbnailImage) }
//                    ) { selectedItems ->
//                        if (selectedItems.isEmpty()) return@startForResult
//                        Log.d(TAG, "선택된 친구:\n${selectedItems.joinToString("\n")}")




                    // 메시지 보낼 친구의 UUID 목록
                    //val receiverUuids = selectedItems
                    var receiverUuids = mutableListOf<String>()

                    for( i in 0 until friends.elements.size)
                    {

                       var friend1 =  friends.elements[i];
                        m_modelList.add(CBranchInfoUser(friend1.profileThumbnailImage, friend1.profileNickname, friend1.uuid))
                    }

                    callAdapter()

                    // Feed 메시지
                    val template = defaultText

                    // 메시지 보내기
//                    TalkApiClient.instance.sendDefaultMessage(receiverUuids, template) { result, error ->
//                        if (error != null) {
//                            Log.e(TAG, "메시지 보내기 실패", error)
//                        }
//                        else if (result != null) {
//                            Log.i(TAG, "메시지 보내기 성공 ${result.successfulReceiverUuids}")
//
//                            if (result.failureInfos != null) {
//                                Log.d(TAG, "메시지 보내기에 일부 성공했으나, 일부 대상에게는 실패 \n${result.failureInfos}")
//                            }
//                        }
//                    }
                }
            }
        }
    }


    fun callAdapter()
    {
        //어답터 인스턴스 생성
//        RecyclerAdapter = RecyclerAdapter()
//        RecyclerAdapter.submitList(m_modelList)
//
//        recyclerview_main.apply{
//
//            //리사이클러뷰 설정
//            layoutManager = LinearLayoutManager(this@SendEventMsgView, LinearLayoutManager.VERTICAL, false)
//            //어답터 장착
//            adapter = RecyclerAdapter
//        }

    }


    fun SendMsg()
    {
        // 메시지 보내기
//        TalkApiClient.instance.sendDefaultMessage(receiverUuids, template) { result, error ->
//            if (error != null) {
//                Log.e(TAG, "메시지 보내기 실패", error)
//            }
//            else if (result != null) {
//                Log.i(TAG, "메시지 보내기 성공 ${result.successfulReceiverUuids}")
//
//                if (result.failureInfos != null) {
//                    Log.d(TAG, "메시지 보내기에 일부 성공했으나, 일부 대상에게는 실패 \n${result.failureInfos}")
//                }
//            }
//        }
    }

}