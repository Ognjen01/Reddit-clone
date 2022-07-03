package com.ognjenlazic.reddit.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.R
import com.ognjenlazic.reddit.navigation.Screen
import com.ognjenlazic.reddit.repository.DataStorePreferenceRepository
import com.ognjenlazic.reddit.ui.theme.Purple700
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.viewModel.SinglePostViewModel
import com.ognjenlazic.reddit.viewModel.SplashScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController) {

    val viewModel = viewModel(modelClass = SplashScreenViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataStorePreferenceRepository: DataStorePreferenceRepository = DataStorePreferenceRepository.getInstance(context)
    val userInfo = dataStorePreferenceRepository.getUserInformation
    viewModel.getCurrentUserInfo(userInfo)

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navController.popBackStack()
        if (viewModel.user.username != ""){
            navController.navigate(Screen.Home.route)
        } else {
            navController.navigate(Screen.Login.route)
        }
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    val logo: Painter = painterResource(id = R.drawable.reddit_logo)

    Box(
        modifier = Modifier
            .background(RedditWhite)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .aspectRatio(logo.intrinsicSize.width / logo.intrinsicSize.height),
            painter = logo,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}