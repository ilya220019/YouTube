package vef.ter.youtube.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vef.ter.youtube.BuildConfig
import vef.ter.youtube.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}