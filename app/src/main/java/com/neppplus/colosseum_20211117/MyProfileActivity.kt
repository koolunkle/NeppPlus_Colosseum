package com.neppplus.colosseum_20211117

import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20211117.databinding.ActivityMyProfileBinding
import com.neppplus.colosseum_20211117.databinding.ActivitySplashBinding
import com.neppplus.colosseum_20211117.utils.ContextUtil

class MyProfileActivity : BaseActivity() {

    lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnLogout.setOnClickListener {

//            진짜 로그아웃할 것인지? 확인
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃 확인")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

//                실제 로그아웃 처리 => 저장된 토큰을 ""으로 돌려주자 (내 폰에 저장된 토근 삭제)

                ContextUtil.setToken(mContext, "")

//                화면 종료 -> (열려있는 모든 화면을 전부 닫고) SplashActivity로 이동

                val myIntent = Intent(mContext, SplashActivity::class.java)
                myIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(myIntent)

            })
            alert.setNegativeButton("취소", null)

            alert.show()

        }

    }

    override fun setValues() {

    }


}