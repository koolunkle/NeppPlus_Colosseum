package com.neppplus.colosseum_20211117.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neppplus.colosseum_20211117.R
import com.neppplus.colosseum_20211117.datas.ReplyData
import com.neppplus.colosseum_20211117.datas.TopicData
import com.neppplus.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class ReplyAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<ReplyData>
) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if (tempRow == null) {

            tempRow = mInflater.inflate(R.layout.reply_list_item, null)

        }

        val row = tempRow!!

        val data = mList[position]

        val txtReplyContent = row.findViewById<TextView>(R.id.txtReplyContent)
        val txtWriterNickname = row.findViewById<TextView>(R.id.txtWriterNickname)
        val txtSelectedSide = row.findViewById<TextView>(R.id.txtSelectedSide)
        val txtCreatedAt = row.findViewById<TextView>(R.id.txtCreatedAt)
        val txtReplyCount = row.findViewById<TextView>(R.id.txtReplyCount)
        val txtLikeCount = row.findViewById<TextView>(R.id.txtLikeCount)
        val txtDislikeCount = row.findViewById<TextView>(R.id.txtDislikeCount)

        txtReplyContent.text = data.content

        txtWriterNickname.text = data.writer.nickname

        txtSelectedSide.text = "(${data.selectedSide.title})"

        txtCreatedAt.text = data.getFormattedCreatedAt()

        txtReplyCount.text = "답글 : ${data.replyCount}개"
        txtLikeCount.text = "좋아요 : ${data.likeCount}개"
        txtDislikeCount.text = "싫어요 : ${data.dislikeCount}개"

//        각 줄의 좋아요 갯수에 이벤트 처리
        txtLikeCount.setOnClickListener {

//            이 댓글에 좋아요를 남겼다고 -> 서버 API 호출
            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, true, object : ServerUtil.JsonResponseHandler {

                override fun onResponse(jsonObj: JSONObject) {

                }

            } )
        
        txtDislikeCount.setOnClickListener {

//            이 댓글에 싫어요를 남겼다고 -> 서버 API 호출

            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, false, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                }

            })

        }

            return row
        }
    }




