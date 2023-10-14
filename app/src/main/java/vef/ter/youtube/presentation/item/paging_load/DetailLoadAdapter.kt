package vef.ter.youtube.presentation.item.paging_load

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import vef.ter.youtube.presentation.playlists.paging_load.DetailViewHolder

class DetailLoadAdapter :
    LoadStateAdapter<DetailViewHolder>() {


    override fun onBindViewHolder(holder: DetailViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DetailViewHolder {
        return DetailViewHolder.create(parent)
    }
}