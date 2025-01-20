package com.example.palindromeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.palindromeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.checkButton.setOnClickListener {
            val text = binding.palindromeInput.text.toString()
            if (isPalindrome(text)) {
                showDialog("isPalindrome")
            } else {
                showDialog("not palindrome")
            }
        }

        binding.nextButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanText = text.lowercase().replace("\\s+".toRegex(), "")
        return cleanText == cleanText.reversed()
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
