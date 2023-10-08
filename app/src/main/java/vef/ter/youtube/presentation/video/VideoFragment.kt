package vef.ter.youtube.presentation.video

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.youtube57.presentation.video.VideoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import vef.ter.youtube.R
import vef.ter.youtube.core.base.BaseFragment
import vef.ter.youtube.data.model.PlayListsModel
import vef.ter.youtube.databinding.FragmentVideoBinding
import vef.ter.youtube.utils.Constants
import vef.ter.youtube.utils.Online

class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>() {

    override val viewModel: VideoViewModel by viewModel()
    private val online: Online by lazy {
        Online(requireContext())
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVideoBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        checkConnection()
        initLiveData()
        initListener()
    }

    private fun initListener() {
        binding.layoutToolbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnDownload.setOnClickListener {
            down()
        }
    }

    private fun down() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(R.layout.layout_download).show()
    }

    private fun init() {
        setFragmentResultListener(Constants.GO_TO_VIDEO_FRAGMENT) { _, bundle ->
            bundle.getSerializable(Constants.SET_ITEM_TO_VIDEO)
                ?.let { item ->
                    val _item = item as PlayListsModel.Item
                    initView(_item.contentDetails.videoId)
                }
        }
    }

    private fun initView(videoId: String) {
        viewModel.getVideo(videoId)
    }

    private fun initLiveData() {
        viewModel.video.observe(viewLifecycleOwner) { item ->
            setView(item.items)
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

    private fun setView(items: List<PlayListsModel.Item>) {
        binding.tvTitle.text = items.first().snippet.title
        binding.tvDescription.text = items.first().snippet.description
        binding.imgVideo.load(items.first().snippet.thumbnails.standard.url)
    }

    private fun checkConnection() {
        online.observe(viewLifecycleOwner) { isConnect ->
            if (!isConnect) {
                binding.llMain.visibility = View.GONE
                binding.toolbar.visibility = View.GONE
                binding.llInclude.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnAgain.setOnClickListener {
                if (isConnect) {
                    binding.llMain.visibility = View.VISIBLE
                    binding.llInclude.visibility = View.GONE
                    binding.toolbar.visibility = View.VISIBLE
                }
            }
        }
    }
}
