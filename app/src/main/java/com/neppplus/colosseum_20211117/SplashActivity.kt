package com.neppplus.colosseum_20211117

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20211117.databinding.ActivitySplashBinding
import com.neppplus.colosseum_20211117.utils.ContextUtil

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//         2.5초가 지나면 어느 화면으로 가는지 검사

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            val myIntent: Intent

//          자동 로그인 할 상황? -> 메인화면
//          아니라면? -> 로그인화면

//            질문? 자동로그인 사용? + 로그인 성공?
            if (ContextUtil.getAutoLogin(mContext) && ContextUtil.getToken(mContext) != "") {
                myIntent = Intent(mContext, MainActivity::class.java)
            } else {
                myIntent = Intent(mContext, LoginActivity::class.java)
            }

            startActivity(myIntent)

        }, 1000)


    }


}