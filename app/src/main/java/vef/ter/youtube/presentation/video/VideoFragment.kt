package vef.ter.youtube.presentation.video

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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


    override fun initListener() {
        binding.layoutToolbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnDownload.setOnClickListener {
            down()
        }
    }

    override fun initView() {
        setFragmentResultListener(Constants.GO_TO_VIDEO_FRAGMENT) { _, bundle ->
            bundle.getSerializable(Constants.SET_ITEM_TO_VIDEO)
                ?.let { item ->
                    val _item = item as PlayListsModel.Item
                    initView(_item.contentDetails.videoId)
                }
        }
    }

    private fun down() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(R.layout.layout_download).show()
    }

       private fun initView(videoId: String) {
        viewModel.getVideo(videoId)
    }

    override fun initLiveData() {
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
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "S0Q4gqBUs7c"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    override fun checkConnection() {
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