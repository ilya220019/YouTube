package vef.ter.youtube.utils

import androidx.recyclerview.widget.DiffUtil
import vef.ter.youtube.data.model.PlayListsModel

object UserComparator : DiffUtil.ItemCallback<PlayListsModel.Item>() {
    override fun areItemsTheSame(
        oldItem: PlayListsModel.Item,
        newItem: PlayListsModel.Item
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PlayListsModel.Item,
        newItem: PlayListsModel.Item
    ): Boolean {
        return oldItem == newItem
    }

}