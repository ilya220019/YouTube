package vef.ter.youtube.presentation.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.databinding.ItemForPlaylistBinding

class DetailItemsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<PlayListsModel.Item>,
    private val onClickItem: (playlistItem: PlayListsModel.Item) -> Unit
) :
    PagingDataAdapter<PlayListsModel.Item, DetailItemsAdapter.PlaylistItemsViewHolder>(
        diffUtilCallback
    ) {

    private val list = mutableListOf<PlayListsModel.Item>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaylistItemsViewHolder(
        ItemForPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PlaylistItemsViewHolder, position: Int) {
        val newPosition = getItem(position)
        newPosition?.let {
            holder.bind(it)
        }
    }

    inner class PlaylistItemsViewHolder(private val binding: ItemForPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayListsModel.Item) {
            binding.tvVideoName.text = item.snippet.title
            binding.imgVideo.load(item.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onClickItem(item) }
        }
    }
}