package vef.ter.youtube.presentation.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slottica.reviewfueatures.youtube57_3.presetation.playlists.PlaylistsViewModel
import vef.ter.youtube.core.base.BaseFragment
import vef.ter.youtube.core.network.RetrofitClient
import vef.ter.youtube.core.utils.Status
import vef.ter.youtube.databinding.FragmentPlayListBinding
import vef.ter.youtube.domain.repository.Repository

class PlayListFragment : BaseFragment<FragmentPlayListBinding>() {
    private val playListsViewModel =
        PlaylistsViewModel(Repository(RetrofitClient().createApiService()))
    private val adapter = PlaylistsAdapter()
    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlayListBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playListsViewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { adapter.addData(it.items) }
                    binding.rv.adapter = adapter

                }

                Status.ERROR -> {


                }

                Status.LOADING -> {


                }

            }
        }
    }
}