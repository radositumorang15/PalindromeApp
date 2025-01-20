package com.example.palindromeapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palindromeapp.Adapter.UserAdapter
import com.example.palindromeapp.databinding.ActivityThirdBinding
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var viewModel: UserViewModel
    private val adapter = UserAdapter { user ->
        val intent = Intent().apply {
            putExtra("selected_user_name", "${user.first_name} ${user.last_name}")
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        setupRecyclerView()
        setupSwipeRefresh()
        observeUsers()
        setupToolbar()

        lifecycleScope.launch {
            viewModel.loadUsers(refresh = true)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.usersList.layoutManager = LinearLayoutManager(this)
        binding.usersList.adapter = adapter
        binding.usersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    lifecycleScope.launch {
                        viewModel.loadUsers(refresh = false)
                    }
                }
            }
        })
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.loadUsers(refresh = true)
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun observeUsers() {
        viewModel.users.observe(this) { users ->
            binding.emptyState.visibility = if (users.isEmpty()) View.VISIBLE else View.GONE
            adapter.submitList(users)
        }
    }
}
