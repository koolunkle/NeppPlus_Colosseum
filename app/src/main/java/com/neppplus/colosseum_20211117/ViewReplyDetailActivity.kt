package com.neppplus.colosseum_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_20211117.databinding.ActivityViewReplyDetailBinding
import com.neppplus.colosseum_20211117.datas.ReplyData
import com.neppplus.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewReplyDetailBinding

    lateinit var mReplyData: ReplyData

    val mReReplyList = ArrayList<ReplyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mReplyData = intent.getSerializableExtra("reply") as ReplyData

        binding.txtSelectedSide.text = "(${mReplyData.selectedSide.title})"

        binding.txtWriterNickname.text = mReplyData.writer.nickname

        binding.txtContent.text = mReplyData.content

        getReplyDetailFromServer()

    }

    fun getReplyDetailFromServer() {

        ServerUtil.getRequestReplyDetail(
            mContext,
            mReplyData.id,
            object : ServerUtil.JsonResponseHandler {

                override fun onResponse(jsonObject: JSONObject) {

                    val dataObj = jsonObject.getJSONObject("data")
                    val replyObj = dataObj.getJSONObject("reply")
                    val repliesArr = replyObj.getJSONArray("replies")

                    for (i in 0 until repliesArr.length()) {

//                    [] => {} (JSONObject) 추출 -> ReplyData로 변환 -> 대댓글 목록에 추가 (최종목표)

                        mReReplyList.add(ReplyData.getReplyDataFromJson(repliesArr.getJSONObject(i)))

                    }

                }

            })

    }


}