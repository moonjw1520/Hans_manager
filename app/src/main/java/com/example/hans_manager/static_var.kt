package com.example.hans_manager

import com.kakao.sdk.template.model.*

val defaultText = TextTemplate(
        text = """
        카카오링크는 카카오 플랫폼 서비스의 대표 기능으로써 사용자의 모바일 기기에 설치된 카카오 플랫폼과 연동하여 다양한 기능을 실행할 수 있습니다.     
    """.trimIndent(),
        link = Link(
                webUrl = "https://developers.kakao.com",
                mobileWebUrl = "https://developers.kakao.com"
        )
)

val defaultFeed = FeedTemplate(
        content = Content(
                title = "딸기 치즈 케익",
                description = "#케익 #딸기 #삼평동 #카페 #분위기 #소개팅",
                imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                link = Link(
                        webUrl = "https://developers.kakao.com",
                        mobileWebUrl = "https://developers.kakao.com"
                )
        ),
        social = Social(
                likeCount = 286,
                commentCount = 45,
                sharedCount = 845
        ),
        buttons = listOf(
                Button(
                        "웹으로 보기",
                        Link(
                                webUrl = "https://developers.kakao.com",
                                mobileWebUrl = "https://developers.kakao.com"
                        )
                ),
                Button(
                        "앱으로 보기",
                        Link(
                                androidExecParams = mapOf("key1" to "value1", "key2" to "value2"),
                                iosExecParams = mapOf("key1" to "value1", "key2" to "value2")
                        )
                )
        )
)