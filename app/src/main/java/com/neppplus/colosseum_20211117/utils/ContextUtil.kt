package com.neppplus.colosseum_20211117.utils

import android.content.Context

class ContextUtil {

    companion object {

        private val prefName = "ColosseumPref"

        private val TOKEN = "TOKEN"

//        token setter 함수

        fun setToken(context: Context, token: String) {

//            1. 메모장 열기 (prefName에 해당하는 SharedPreferences 열기)

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

//            2. 토큰값을 TOKEN 항목에 저장

            pref.edit().putString(TOKEN, token).apply()

        }

//        token getter 함수


    }

}


