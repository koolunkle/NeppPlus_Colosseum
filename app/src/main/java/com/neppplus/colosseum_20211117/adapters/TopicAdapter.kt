package com.neppplus.colosseum_20211117.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.neppplus.colosseum_20211117.datas.TopicData

class TopicAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<TopicData>
) : ArrayAdapter<TopicData>(mContext, resId, mList) {


}