package vef.ter.youtube.presentation.playlists

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.databinding.ItemBinding

class PlaylistsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<PlayListsModel.Item>,
    private val onClickItem: (playlistItem: PlayListsModel.Item) -> Unit
) :
    PagingDataAdapter<PlayListsModel.Item, PlaylistsAdapter.PlaylistsViewHolder>(diffUtilCallback) {

    private var list = mutableListOf<PlayListsModel.Item>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder {
        return PlaylistsViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        val newPosition = getItem(position)
        newPosition?.let {
            holder.toBind(it)
        }
    }

    inner class PlaylistsViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun toBind(item: PlayListsModel.Item) {
            binding.tvPlaylistName.text = item.snippet.title
            binding.tvPlaylistVideoCount.text =
                item.contentDetails.itemCount.toString() + " video series"
            binding.imgVideo.load(item.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onClickItem(item) }

        }
    }
}