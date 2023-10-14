package vef.ter.youtube.presentation.playlists.paging_load

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class PlayListsLoadAdapter :
    LoadStateAdapter<DetailViewHolder>() {


    override fun onBindViewHolder(holder: DetailViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DetailViewHolder {
        return PlaylistsLoadViewHolder.create(parent)
    }
}