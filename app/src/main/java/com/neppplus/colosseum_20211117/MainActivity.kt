package com.neppplus.colosseum_20211117

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.provider.Settings.System.getString
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20211117.databinding.ActivityMainBinding
import com.neppplus.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSignUp.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnLogin.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()

//            서버에서 이메일 / 비밀번호가 맞는 계정인지? 로그인 요청

            ServerUtil.postRequestLogin(
                inputEmail,
                inputPw,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObject: JSONObject) {

//                    로그인 API를 호출하고 돌아온 상황
//                    결과로 jsonObj 하나를 받아서 돌아온 상황

                        Log.d("화면에서의 jsonObj", jsonObject.toString())

                        val code = jsonObject.getInt("code")

                        Log.d("로그인 코드 값", code.toString())

//                        code : 200 -> 로그인 성공 토스트
//                        그 외 -> 로그인 실패 토스트

                        runOnUiThread {

                            if (code == 200) {

//                                연습문제. 로그인 성공시 그 사람의 닉네임을 추출해서
//                                "~~님, 환영합니다!" 토스트 출력

                                val dataObj = jsonObject.getJSONObject("data")
                                val userObj = dataObj.getJSONObject("user")
                                val nick_name = userObj.getString("nick_name")

                                Toast.makeText(mContext, "${nick_name}님, 환영합니다!", Toast.LENGTH_SHORT).show()
                            } else {
//                                  message String으로 실패 사유를 알려준다.
//                                  JSON Parsing으로 추출해서 -> "로그인 실패" 대신 서버가 알려준 실패 사유를 띄우자.

                                val message = jsonObject.getString("message")

                                Log.d("로그인 실패 사유", message.toString())

                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }

                        }


                    }


                })

        }

    }

    override fun setValues() {

    }


}
