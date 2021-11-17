package com.neppplus.colosseum_20211117.utils

import android.provider.ContactsContract
import android.util.Log
import okhttp3.FormBody
import okhttp3.Request

class ServerUtil {

    companion object {

//        여기에 적는 변수 / 함수는 => JAVA의 static에 대응됨.
//        클래스이름.기능() 으로 활용 가능.

//        모든 함수 (기능) 가 공유할 서버 컴퓨터 주소.

        val HOST_URL = "http://54.180.52.26"

//        로그인 함수 - POST

        fun postRequestLogin(email: String, pw: String) {

//            1. 어디로 가야? URL

            val urlString = "${HOST_URL}/user"

//            2. 어떤 데이터를 들고가는가? (파라미터)

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//            3. 어떤 메소드 + 1/2 데이터 결합 => 어떤 요청인지 완성

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

        }

    }

}