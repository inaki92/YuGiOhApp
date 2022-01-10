package com.inaki.yugiohapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inaki.yugiohapp.databinding.ActivityMainBinding
import com.inaki.yugiohapp.views.CardsFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.my_frag_container, CardsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}