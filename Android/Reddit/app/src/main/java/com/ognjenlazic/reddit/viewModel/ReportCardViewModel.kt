package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.reddit.model.Post
import com.ognjenlazic.reddit.model.Report
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.RetrofitInstance.Companion.report
import com.ognjenlazic.reddit.network.model.CommentResponse
import com.ognjenlazic.reddit.network.model.ReportResponse
import kotlinx.coroutines.launch

class ReportCardViewModel(): ViewModel() {

    var comment:CommentResponse by mutableStateOf(CommentResponse())
    var post: Post by mutableStateOf(Post())
    var report: ReportResponse by mutableStateOf(ReportResponse())

    fun getEntity(){
        viewModelScope.launch {
            if(report.postId != null){
                try {
                    post = RetrofitInstance.post.getPostById(report.postId!!)
                } catch (e: Exception){
                    Log.e("Loading", e.message.toString())
                }
            } else if (report.commentId != null){
                try {
                    comment = RetrofitInstance.comment.getCommentById(report.commentId!!)
                } catch (e: Exception){
                    Log.e("Loading", e.message.toString())
                }
            }
        }
    }

    fun acceptReport(){
        viewModelScope.launch {
            try {
                var reportToSave = Report()
                reportToSave.accepted = true
                reportToSave.reportId = report.reportId
                reportToSave.postId = report.postId
                reportToSave.commentId = report.commentId
                reportToSave.reason = report.reason
                reportToSave.timestamp = "2022-02-12T13:14:14"
                reportToSave.userId = report.userId

                RetrofitInstance.report.editReport(reportToSave)
                // Apdejtovanje reporta i brisanje objave ili komentara
                if (report.postId != null){
                    RetrofitInstance.post.deletePost(report.postId!!)
                } else if (report.commentId != null){
                    RetrofitInstance.comment.deleteComment(report.commentId!!)
                }
            } catch (e: Exception){

            }
        }
    }

    fun declineReport(){
        viewModelScope.launch {
            try {
                RetrofitInstance.report.deleteReport(reportId = report.reportId)
            } catch (e: Exception){
                Log.e("Error", "Error while deleting report")
            }
        }
    }
}