package com.example.hans_manager

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    companion object{
        lateinit var  instance: GlobalApplication
            private  set
    }

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "fbbfa34373115fff0c5b660da49f1cc9")
        instance = this
    }
}