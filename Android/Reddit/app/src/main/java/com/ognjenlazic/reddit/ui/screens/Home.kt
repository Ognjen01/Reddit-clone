package com.ognjenlazic.reddit.ui.screens

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.widget.Space
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.model.ReportTypes
import com.ognjenlazic.reddit.ui.elements.PostCard
import com.ognjenlazic.reddit.viewModel.PostsViewModel
import java.lang.Math.sqrt
import java.util.*


@Composable
@ExperimentalFoundationApi
fun HomeScreen(navController: NavHostController) {
    val postsViewModel = viewModel(modelClass = PostsViewModel::class.java)

    var acceleration = 0f
    var currentAcceleration = 0f
    var lastAcceleration = 0f
    val context = LocalContext.current

    val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if (acceleration > 12) {
                Toast.makeText(context, "Sorting with shake!", Toast.LENGTH_SHORT).show()
                postsViewModel.getSortedPosts("Newest")

            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    val sensorManager = LocalContext.current.getSystemService(SENSOR_SERVICE) as SensorManager
    Objects.requireNonNull(sensorManager)!!.registerListener(sensorListener, sensorManager!!
        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    acceleration = 10f
    currentAcceleration = SensorManager.GRAVITY_EARTH
    lastAcceleration = SensorManager.GRAVITY_EARTH

    sensorManager.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
        Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
    )

//    postsViewModel.getPosts()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(items = postsViewModel.posts) { index, post ->
                if(index== 0){

                    var mExpanded by remember { mutableStateOf(false) }

                    var mSelectedText by remember { mutableStateOf("") }

                    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
                    val reportReasons = arrayOf("Popular", "Unpopular", "Newest", "Oldest")

                    val icon = if (mExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = mSelectedText,
                        onValueChange = {
                            mSelectedText = it
                            postsViewModel.selectedSortType = it
                        },
                        modifier = Modifier
                            .width(280.dp)
                            .onGloballyPositioned { coordinates ->
                                mTextFieldSize = coordinates.size.toSize()
                            },
                        label = { Text("Sort") },
                        trailingIcon = {
                            Icon(icon, "contentDescription",
                                Modifier.clickable { mExpanded = !mExpanded })
                        }
                    )
                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                    ) {
                        reportReasons.forEach { label ->
                            DropdownMenuItem(onClick = {
                                mSelectedText = label
                                mExpanded = false
                                postsViewModel.getSortedPosts(mSelectedText)
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
                PostCard(post, navController)
                if (index == postsViewModel.posts.size - 1){
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }
}