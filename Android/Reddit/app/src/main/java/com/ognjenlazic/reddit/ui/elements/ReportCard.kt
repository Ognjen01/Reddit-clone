package com.ognjenlazic.reddit.ui.elements

import android.util.Log
import androidx.compose.material.*
import com.ognjenlazic.reddit.ui.theme.RedditWhite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.network.model.ReportResponse
import com.ognjenlazic.reddit.ui.theme.RedditGreen
import com.ognjenlazic.reddit.ui.theme.RedditRed
import com.ognjenlazic.reddit.viewModel.ReportCardViewModel

@Composable
fun ReportCard(navHostController: NavHostController, report: ReportResponse) {
    val viewModel = ReportCardViewModel()
    viewModel.report = report
    viewModel.getEntity()

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (report.postId != null) {
                PostCard(viewModel.post, navHostController)
            } else if (report.commentId != null) {
                CommentCard(comment = viewModel.comment)
            }
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .padding(10.dp)

            ) {
                Text(
                    text = "r/${report.userId}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = report.reason,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.acceptReport()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = RedditGreen)
                ) {
                    Text(
                        text = "Accept",
                        color = Color.Black,
                    )
                }

                Button(
                    onClick = {
                        viewModel.declineReport()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = RedditRed)
                ) {
                    Text(
                        text = "Decline",
                        color = Color.Black,
                    )
                }
            }
        }
    }
}