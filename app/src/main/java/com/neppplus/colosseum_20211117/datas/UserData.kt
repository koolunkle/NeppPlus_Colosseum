package com.neppplus.colosseum_20211117.datas

import org.json.JSONObject
import java.io.Serializable

class UserData : Serializable {

    var id = 0
    var email = ""
    var nickname = ""

    companion object {

        fun getUserDataFromJson(jsonObject: JSONObject): UserData {

            val userData = UserData()

            userData.id = jsonObject.getInt("id")
            userData.email = jsonObject.getString("email")
            userData.nickname = jsonObject.getString("nick_name")

            return userData

        }

    }


}