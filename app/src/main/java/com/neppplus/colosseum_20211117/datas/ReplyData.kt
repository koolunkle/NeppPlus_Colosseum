package com.neppplus.colosseum_20211117.datas

import org.json.JSONObject
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class ReplyData : Serializable {

    var id = 0
    var content = ""

    var writer = UserData()  // 사용자 데이터가 들어올 것이라고 명시

//    어느 진영에 대한 댓글?

    var selectedSide = SideData()

//    언제 적힌 댓글?
    var createdAt = Calendar.getInstance() // 기본 Calendar 대입 -> 현재 일시가 저장됨

//    좋아요 / 싫어요 / 댓글 관련 숫자
    var likeCount = 0
    var dislikeCount = 0
    var myLike = false
    var myDislike = false
    var replyCount = 0

//    내 핸드폰의 시간을 고려해서, 시차를 보정해서 / 가공해서 작성일시를 알려주는 함수

    fun getFormattedCreatedAt(): String {

//        서버가 주는 시간 : GMT +0
//        내 폰의 시간 : 설정된 GMT +? 시차가 얼마나 나는가?
//        ex. 서울 -> +9 => 게시글 작성시간도 9시간 더해서 보여주자

//        내 핸드폰에 설정된 시간대가 어느 시간대? => ex. 서울
        val timeZone = Calendar.getInstance().timeZone

//        몇시간 차이? rawOffset - 시차값 (ms 단위) => 시간단위로 변경

        val timeOffset = timeZone.rawOffset / 1000 / 60 / 60

//        이 글의 작성시간을 시차만큼 더해주자 => 표준시간 / 내 핸드폰 (로컬시간) 보정

        val tempCreatedAt = Calendar.getInstance()
        tempCreatedAt.time = this.createdAt.time
        tempCreatedAt.add(Calendar.HOUR, timeOffset)

//        결과 String으로 가공

        val sdf = SimpleDateFormat("yyyy/MM/dd a h시 m분")

        return sdf.format(tempCreatedAt.time)

    }

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

//            좋아요 / 싫어요 / 댓글 갯수 등등

            replyData.likeCount = jsonObject.getInt("like_count")
            replyData.dislikeCount = jsonObject.getInt("dislike_count")
            replyData.myLike = jsonObject.getBoolean("my_like")
            replyData.myDislike = jsonObject.getBoolean("my_dislike")
            replyData.replyCount = jsonObject.getInt("reply_count")

            return replyData

        }

    }

}