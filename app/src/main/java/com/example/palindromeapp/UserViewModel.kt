package com.example.palindromeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.palindromeapp.data.RetrofitClient
import com.example.palindromeapp.data.User

class UserViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private var currentPage = 1
    private var isLastPage = false
    private val api = RetrofitClient.api

    suspend fun loadUsers(refresh: Boolean = false) {
        try {
            if (refresh) {
                currentPage = 1
                isLastPage = false
            }

            if (!isLastPage) {
                val response = api.getUsers(currentPage, 10)
                if (refresh) {
                    _users.value = response.data
                } else {
                    _users.value = _users.value.orEmpty() + response.data
                }
                isLastPage = currentPage >= response.total_pages
                currentPage++
            }
        } catch (e: Exception) {
        }
    }
}