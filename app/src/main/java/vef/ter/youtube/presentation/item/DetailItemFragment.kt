package vef.ter.youtube.presentation.item

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import vef.ter.youtube.R
import vef.ter.youtube.core.base.BaseFragment
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.databinding.FragmentDetailItemBinding
import vef.ter.youtube.presentation.item.paging_load.DetailLoadAdapter
import vef.ter.youtube.utils.Constants
import vef.ter.youtube.utils.Online
import vef.ter.youtube.utils.UserComparator

class DetailItemFragment : BaseFragment<FragmentDetailItemBinding, DetailItemsViewModel>() {
    private val adapter = DetailItemsAdapter(UserComparator, this::onClickItem)
    override val viewModel: DetailItemsViewModel by viewModel()
    private val online: Online by lazy {
        Online(requireContext())
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailItemBinding.inflate(inflater, container, false)

    override fun initListener() {
        // TODO("Not yet implemented")
    }

    override fun initView() {

    }

    override fun initLiveData() {
        setFragmentResultListener(Constants.GO_TO_DETAIL_FRAGMENT) { _, bundle ->
            bundle.getSerializable(Constants.SET_ITEM)
                ?.let { item ->
                    val _item = item as PlayListsModel.Item
                    setView(_item)

                    Log.e("ololo", "initLiveData: ${_item.id}")
                    viewModel.getPagingPlaylists(_item.id).observe(viewLifecycleOwner) {
                        binding.rvPlaylistItems.adapter = adapter.withLoadStateHeaderAndFooter(
                            header = DetailLoadAdapter(),
                            footer = DetailLoadAdapter()
                        )
                        viewModel.viewModelScope.launch(Dispatchers.IO) {
                            lifecycle
                            adapter.submitData(it)
                        }
                        adapter.retry()
                        adapter.refresh()
                    }
                }
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
                binding.llItems.visibility = View.GONE
                binding.llInclude.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnAgain.setOnClickListener {
                if (isConnect) {
                    binding.llItems.visibility = View.VISIBLE
                    binding.llInclude.visibility = View.GONE
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setView(item: PlayListsModel.Item) {
        binding.tvTitle.text = item.snippet.title
        binding.tvDescription.text = item.snippet.description
        binding.tvVideo.text = item.contentDetails.itemCount.toString() + " video series"
        binding.layoutToolbarItems.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onClickItem(playlistItem: PlayListsModel.Item) {
        setFragmentResult(
            Constants.GO_TO_VIDEO_FRAGMENT,
            bundleOf(Constants.SET_ITEM_TO_VIDEO to playlistItem)
        )
        findNavController().navigate(R.id.videoFragment)
    }

}