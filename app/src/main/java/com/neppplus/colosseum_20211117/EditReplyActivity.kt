package com.neppplus.colosseum_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20211117.databinding.ActivityEditReplyBinding

class EditReplyActivity : BaseActivity() {

    lateinit var binding: ActivityEditReplyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_reply)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

    }

    override fun setValues() {

    }


}