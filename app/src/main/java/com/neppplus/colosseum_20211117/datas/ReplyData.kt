package com.neppplus.colosseum_20211117.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ReplyData {

    var id = 0
    var content = ""

    var writer = UserData()  // 사용자 데이터가 들어올 것이라고 명시

//    어느 진영에 대한 댓글?

    var selectedSide = SideData()

//    언제 적힌 댓글?
    var createdAt = Calendar.getInstance() // 기본 Calendar 대입 -> 현재 일시가 저장됨

    companion object {

        fun getReplyDataFromJson(jsonObject: JSONObject): ReplyData {

            val replyData = ReplyData()

            replyData.id = jsonObject.getInt("id")
            replyData.content = jsonObject.getString("content")

            val userObj = jsonObject.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromJson(userObj)

            val sideObj = jsonObject.getJSONObject("selected_side")
            replyData.selectedSide = SideData.getSideDataFromJson(sideObj)

//            작성일시 파싱 -> String으로 서버가 내려줌 (String으로 임시 추출)
            val createdAtStr = jsonObject.getString("created_at")

//            임시 추출 (String -> Calendar) 형태로 변환 -> 댓글데이터의 작성일시로 반영

//            서버가 준 String을 분석하기 위한 SimpleDateFormat 만들기
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

//            parse로 Date 형태의 일시 생성 -> Calendar변수.time에 대입
            replyData.createdAt.time = sdf.parse(createdAtStr)

            return replyData

        }

    }

}