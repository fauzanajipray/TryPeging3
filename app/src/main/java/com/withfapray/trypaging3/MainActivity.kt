package com.withfapray.trypaging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.withfapray.trypaging3.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CEK RESULT"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter : RecyclerViewAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel  = ViewModelProvider(this).get(MainViewModel::class.java)

        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            rvAdapter = RecyclerViewAdapter()
            adapter = rvAdapter
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.getListData().collectLatest {
                rvAdapter.submitData(it)
            }
        }
    }
}