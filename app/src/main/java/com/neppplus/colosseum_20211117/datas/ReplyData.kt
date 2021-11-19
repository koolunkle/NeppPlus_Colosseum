package com.neppplus.colosseum_20211117.datas

import org.json.JSONObject

class ReplyData {

    var id = 0
    var content = ""

    var writer = UserData()  // 사용자 데이터가 들어올 것이라고 명시

//    어느 진영에 대한 댓글?

    var selectedSide = SideData()

    companion object {

        fun getReplyDataFromJson(jsonObject: JSONObject): ReplyData {

            val replyData = ReplyData()

            replyData.id = jsonObject.getInt("id")
            replyData.content = jsonObject.getString("content")

            val userObj = jsonObject.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromJson(userObj)

            val sideObj = jsonObject.getJSONObject("selected_side")
            replyData.selectedSide = SideData.getSideDataFromJson(sideObj)

            return replyData

        }

    }

}