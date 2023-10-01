package vef.ter.youtube.presentation.nocon

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vef.ter.youtube.databinding.FragmentNoConnectBinding

class NoConnectFragment : Fragment() {
    private lateinit var binding: FragmentNoConnectBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoConnectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAgain.setOnClickListener {
            hasConnection()
        }
    }

    private fun hasConnection() {
        val cm =
            requireContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionInfo = cm.activeNetwork
        if (connectionInfo != null) {
            findNavController().navigateUp()
        }
    }
}
