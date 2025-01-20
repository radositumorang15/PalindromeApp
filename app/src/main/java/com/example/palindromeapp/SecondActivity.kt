package com.example.palindromeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.palindromeapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupViews()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViews() {
        val name = intent.getStringExtra("name") ?: ""
        binding.nameText.text = name
    }

    private fun setupListeners() {
        binding.chooseUserButton.setOnClickListener {
            startActivityForResult(Intent(this, ThirdActivity::class.java), USER_SELECT_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == USER_SELECT_REQUEST && resultCode == RESULT_OK) {
            val selectedName = data?.getStringExtra("selected_user_name")
            binding.selectedUserText.text = selectedName
        }
    }

    companion object {
        const val USER_SELECT_REQUEST = 1001
    }
}
