package com.ooommm.clontelegramm3.utilits

import androidx.recyclerview.widget.DiffUtil
import com.ooommm.clontelegramm3.models.CommonModel

class DiffUtilCallBack(
    private val oldList: List<CommonModel>,
    private val newList: List<CommonModel>

) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val result =
            oldList.get(oldItemPosition).timeStamp == newList.get(newItemPosition).timeStamp
        return result
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val result =
            oldList.get(oldItemPosition) == newList.get(newItemPosition)
        return result
    }
}