package vef.ter.youtube.presentation.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import vef.ter.youtube.R
import vef.ter.youtube.core.base.BaseFragment
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.databinding.FragmentPlayListBinding
import vef.ter.youtube.presentation.MainActivity
import vef.ter.youtube.utils.Constants
import vef.ter.youtube.utils.Online

class PlayListFragment : BaseFragment<FragmentPlayListBinding, PlaylistsViewModel>() {
    private val adapter = PlaylistsAdapter(this::onClickItem)

    override val viewModel: PlaylistsViewModel by viewModel()
    private val aOnline: Online by lazy {
        Online(requireContext())
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentPlayListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connect()
        initView()
        initLiveData()
    }

    private fun initLiveData() {
        viewModel.playlists.observe(viewLifecycleOwner) { list ->
            init(list.items)
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun connect() {
        aOnline.observe(viewLifecycleOwner) { isConnect ->
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

    private fun init(items: List<PlayListsModel.Item>) {
        adapter.addData(items)
        binding.rv.adapter = adapter
    }


    private fun initView() {
        viewModel.getPlaylists()
        viewModel.playlists.observe(viewLifecycleOwner) {
            adapter.addData(it.items)
        }
    }


    private fun onClickItem(playlistItem: PlayListsModel.Item) {
        setFragmentResult(
            Constants.GO_TO_DETAIL_FRAGMENT,
            bundleOf(Constants.SET_ITEM to playlistItem)
        )
        findNavController().navigate(R.id.detailItemFragment)
    }
}
