package vef.ter.youtube.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import vef.ter.youtube.R
import vef.ter.youtube.core.base.BaseFragment
import vef.ter.youtube.core.utils.Status
import vef.ter.youtube.databinding.FragmentPlayListBinding
import vef.ter.youtube.presentation.MainActivity
import vef.ter.youtube.utils.Online

internal class PlayListFragment : BaseFragment<FragmentPlayListBinding, PlaylistsViewModel>() {
    private val playListsViewModel = PlaylistsViewModel(MainActivity.repository)
    private val adapter = PlaylistsAdapter()
    override val viewModel: PlaylistsViewModel
        get() = MainActivity.repository as PlaylistsViewModel

    override fun inflaterViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentPlayListBinding.inflate(inflater, container, false)


    override fun init() {
        playListsViewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { adapter.addData(it.items) }
                    binding.rv.adapter = adapter
                    conect()
                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "${resource.message} ${resource.code}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun conect() {
        Online(requireContext()).observe(viewLifecycleOwner) {
            if (!it) {
                findNavController().navigate(R.id.noConnectFragment)
            }
        }
    }


}