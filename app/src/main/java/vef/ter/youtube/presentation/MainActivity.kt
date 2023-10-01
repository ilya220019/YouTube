package vef.ter.youtube.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vef.ter.youtube.core.network.RemoteDataSource
import vef.ter.youtube.core.network.RetrofitClient
import vef.ter.youtube.databinding.ActivityMainBinding
import vef.ter.youtube.domain.repository.Repository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        private val retrofit = RetrofitClient().createApiService()
        private val remoteDataSource = RemoteDataSource(retrofit)
        internal val repository = Repository(remoteDataSource)
    }
}