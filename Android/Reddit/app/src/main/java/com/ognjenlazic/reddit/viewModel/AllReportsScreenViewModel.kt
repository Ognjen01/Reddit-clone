package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.ReportResponse
import kotlinx.coroutines.launch

class AllReportsScreenViewModel: ViewModel() {

    var reports:List<ReportResponse> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getAllReports(){
        viewModelScope.launch {
            try {
                val reportList = RetrofitInstance.report.getAllReports()
                reports = reportList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}