package vef.ter.youtube.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vef.ter.youtube.core.base.BaseFragment
import vef.ter.youtube.databinding.FragmentPlayListBinding
import vef.ter.youtube.presentation.MainActivity
import vef.ter.youtube.utils.Online

internal class PlayListFragment : BaseFragment<FragmentPlayListBinding, PlaylistsViewModel>() {
    private val adapter = PlaylistsAdapter()
    override val viewModel: PlaylistsViewModel
        get() = PlaylistsViewModel(MainActivity.repository)

    override fun inflaterViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentPlayListBinding.inflate(inflater, container, false)

    override fun connect() {
        Online(requireContext()).observe(viewLifecycleOwner) { isConnect ->
            if (!isConnect) {
                binding.rv.visibility = View.GONE
                binding.iNoConnect.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnAgain.setOnClickListener {
                if (isConnect) {
                    binding.rv.visibility = View.VISIBLE
                    binding.iNoConnect.visibility = View.GONE
                }
            }
        }
    }


    override fun initView() {
        viewModel.getPlaylists()
        viewModel.playLists.observe(viewLifecycleOwner) {
            adapter.addData(it.items)
        }
    }

    override fun init() {
        viewModel.playLists.observe(viewLifecycleOwner) {
            adapter.addData(it.items)
        }
        binding.rv.adapter = adapter
    }
}
