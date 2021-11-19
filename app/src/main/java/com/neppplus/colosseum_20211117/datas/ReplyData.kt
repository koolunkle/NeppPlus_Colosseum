package com.neppplus.colosseum_20211117.datas

import org.json.JSONObject

class ReplyData {

    var id = 0
    var content = ""

    companion object {

        fun getReplyDataFromJson(jsonObject: JSONObject): ReplyData {

            val replyData = ReplyData()

            replyData.id = jsonObject.getInt("id")
            replyData.content = jsonObject.getString("content")

            return replyData

        }

    }

}