package vef.ter.youtube.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import vef.ter.youtube.R
import vef.ter.youtube.core.base.BaseFragment
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.databinding.FragmentPlayListBinding
import vef.ter.youtube.presentation.item.paging_load.DetailLoadAdapter
import vef.ter.youtube.utils.Constants
import vef.ter.youtube.utils.Online
import vef.ter.youtube.utils.UserComparator

class PlayListFragment : BaseFragment<FragmentPlayListBinding, PlaylistsViewModel>() {
    private val adapter = PlaylistsAdapter(UserComparator, this::onClickItem)

    override val viewModel: PlaylistsViewModel by viewModel()
    private val online: Online by lazy {
        Online(requireContext())
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentPlayListBinding.inflate(inflater, container, false)

    override fun initListener() {
 //       TODO("Not yet implemented")
    }

    override fun initLiveData() {
        viewModel.getPagingPlaylists().observe(viewLifecycleOwner){
            binding.rv.adapter = adapter.withLoadStateHeaderAndFooter(
                header = DetailLoadAdapter(),
                footer = DetailLoadAdapter()

            )
            viewModel.viewModelScope.launch(Dispatchers.IO){
                lifecycle
                adapter.submitData(it)
            }
            adapter.retry()
            adapter.refresh()

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

    override fun checkConnection() {
        online.observe(viewLifecycleOwner) { isConnect ->
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

    }


    private fun onClickItem(playlistItem: PlayListsModel.Item) {
        setFragmentResult(
            Constants.GO_TO_DETAIL_FRAGMENT,
            bundleOf(Constants.SET_ITEM to playlistItem)
        )
        findNavController().navigate(R.id.detailItemFragment)
    }
}