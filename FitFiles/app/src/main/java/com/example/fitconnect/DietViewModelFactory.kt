package com.example.fitconnect

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitconnect.DietViewModel

class DietViewModelFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DietViewModel::class.java)) {
            return DietViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
