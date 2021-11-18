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
import com.neppplus.colosseum_20211117.datas.TopicData

class TopicAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<TopicData>
) : ArrayAdapter<TopicData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if (tempRow == null) {

            tempRow = mInflater.inflate(R.layout.topic_list_item, null)

        }

        val row = tempRow!!

        val data = mList[position]

        val imgTopic = row.findViewById<ImageView>(R.id.imgTopic)
        val txtTopicTitle = row.findViewById<TextView>(R.id.txtTopicTitle)

        txtTopicTitle.text = data.title

        Glide.with(mContext).load(data.imageURL).into(imgTopic)

        return row

    }


}