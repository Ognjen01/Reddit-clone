package com.ognjenlazic.reddit.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ognjenlazic.reddit.R
import com.ognjenlazic.reddit.model.User
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.network.model.UserResponse
import com.ognjenlazic.reddit.ui.theme.RedditWhite

@Composable
fun UserInfoCard(navController: NavController, user: UserResponse) {
    val avatar: Painter = painterResource(id = R.drawable.avatar_one)

    Card(
        modifier = Modifier.clickable { println("Kliknuli ste karticu") },
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column( modifier = Modifier
            .background(Color.Gray)) {

            Row(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .aspectRatio(avatar.intrinsicSize.width / avatar.intrinsicSize.height),
                    painter = avatar,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                Column() {
                    Text(
                        text = user.username,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Column() {
                    Text(
                        text = user.description,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}