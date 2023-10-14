package vef.ter.youtube.presentation.playlists.paging_load

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import vef.ter.youtube.R
import vef.ter.youtube.databinding.ItemProgressBarBinding

class PlaylistsLoadViewHolder(
    binding: ItemProgressBarBinding,
):ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        loadState.endOfPaginationReached
    }

    companion object {


        fun create(parent: ViewGroup): DetailViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_progress_bar,
                parent, false
            )
            val binding = ItemProgressBarBinding.bind(view)
            return DetailViewHolder(binding)
        }
    }
}