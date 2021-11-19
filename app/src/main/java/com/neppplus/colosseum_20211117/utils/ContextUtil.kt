package com.neppplus.colosseum_20211117.utils

import android.content.Context
import android.provider.ContactsContract

class ContextUtil {

    companion object {

        private val prefName = "ColosseumPref"

        private val TOKEN = "TOKEN"

        private val LOGIN_EMAIL = "LOGIN_EMAIL"

        private val AUTO_LOGIN = "AUTO_LOGIN"

        fun setAutoLogin(context: Context, autoLogin: Boolean) {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, autoLogin).apply()
        }

        fun getAutoLogin(context: Context): Boolean {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)

        }

        fun setLoginEmail(context: Context, email: String) {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            pref.edit().putString(LOGIN_EMAIL, email).apply()

        }

//        token setter 함수

        fun setToken(context: Context, token: String) {

//            1. 메모장 열기 (prefName에 해당하는 SharedPreferences 열기)

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

//            2. 토큰값을 TOKEN 항목에 저장

            pref.edit().putString(TOKEN, token).apply()

        }

        fun getLoginEmail(context: Context): String {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(LOGIN_EMAIL, "")!!

        }

//        token getter 함수

        fun getToken(context: Context): String {

//            1. 메모장 열기

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

//            2. 저장된 토큰값을 리턴

            return pref.getString(TOKEN, "")!!

        }


    }

}


