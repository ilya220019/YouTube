package vef.ter.youtube.presentation.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.databinding.ItemForPlaylistBinding

class DetailItemsAdapter(private val onClickItem: (playlistItem: PlayListsModel.Item) -> Unit) :
    RecyclerView.Adapter<DetailItemsAdapter.PlaylistItemsViewHolder>() {

    private var _list = mutableListOf<PlayListsModel.Item>()
    private val list: List<PlayListsModel.Item> get() = _list

    fun addData(playlistModelItem: List<PlayListsModel.Item>) {
        _list.clear()
        _list.addAll(playlistModelItem)
        notifyItemRangeInserted(_list.size, playlistModelItem.size - _list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaylistItemsViewHolder(
        ItemForPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PlaylistItemsViewHolder, position: Int) {
        holder.bind(list[position])
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