package com.ognjenlazic.reddit.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ognjenlazic.reddit.network.RetrofitInstance.Companion.flair
import com.ognjenlazic.reddit.ui.theme.RedditGreen
import com.ognjenlazic.reddit.ui.theme.RedditLightBlue
import com.ognjenlazic.reddit.ui.theme.RedditRed
import com.ognjenlazic.reddit.ui.theme.RedditWhite
import com.ognjenlazic.reddit.viewModel.EditCommunityScreenViewModel

@Composable
fun EditCommunityScreen(navController: NavHostController, communityId: Int) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel(modelClass = EditCommunityScreenViewModel::class.java)
    viewModel.communityId = communityId
    viewModel.getCommunity()
    viewModel.getCommunityFlairs()

    val openCreateRuleDialog = remember { mutableStateOf(false) }
    val openCreateFlairDialog = remember { mutableStateOf(false) }
    var openEditFlairDialog = remember { mutableStateOf(false) }
    val openEditRuleDialog = remember { mutableStateOf(false) }
    val flairIdToEdit = remember { mutableStateOf(0) }
    val ruleIdToEdit = remember { mutableStateOf(0) }


    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Gray)
            .padding(30.dp)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Edit community", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = viewModel.communityName,
                label = { Text(text = "Community name") },
                onValueChange = { viewModel.communityName = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .height(150.dp)
                    .width(280.dp),
                value = viewModel.communityDesc,
                label = { Text(text = "Description") },
                onValueChange = { viewModel.communityDesc = it },
            )
            Spacer(modifier = Modifier.height(16.dp))

            // All ruler
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)
            ) {
                Column(
                    Modifier
                        .padding(10.dp),
                ) {
                    Text(
                        text = "Rules:",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    for (rule in viewModel.community.rules!!) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Gray)
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {

                                Text(
                                    modifier = Modifier.padding(2.dp),
                                    text = rule.description,
                                    color = Color.White,
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(modifier = Modifier.background(Color.Gray)) {
                                Button(
                                    onClick = { viewModel.deleteRule(rule.ruleId) },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = RedditRed)
                                ) {
                                    Text(text = "Delete")
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(
                                    onClick = {
                                        ruleIdToEdit.value = rule.ruleId
                                        viewModel.editRule = rule.description
                                        openEditRuleDialog.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = RedditGreen)
                                ) {
                                    Text(text = "Edit")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Button(
                        onClick = {
                            viewModel.newRule = ""
                            openCreateRuleDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedditGreen)
                    ) {
                        Text(text = "Add new")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // All flairs
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)
            ) {
                Column(
                    Modifier
                        .padding(10.dp),
                ) {
                    Text(
                        text = "Flairs:",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    for (flair in viewModel.flairList) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Gray)
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {

                                Text(
                                    modifier = Modifier.padding(2.dp),
                                    text = flair.name,
                                    color = Color.White,
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(modifier = Modifier.background(Color.Gray)) {
                                Button(
                                    onClick = { viewModel.deleteFlair(flair.flairId) },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = RedditRed)
                                ) {
                                    Text(text = "Delete")
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(
                                    onClick = {
                                        flairIdToEdit.value = flair.flairId
                                        viewModel.editFlair = flair.name
                                        openEditFlairDialog.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = RedditGreen)
                                ) {
                                    Text(text = "Edit")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Button(
                        onClick = {
                            viewModel.newFlair = ""
                            openCreateFlairDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedditGreen)
                    ) {
                        Text(text = "Add new")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.saveCommunityInformation() }) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }

    //TODO: Create rule

    if (openCreateRuleDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openCreateRuleDialog.value = false
            },
            backgroundColor = Color.Gray,
            title = {
                Text(text = "New rule")
            },
            text = {
                OutlinedTextField(
                    modifier = Modifier
                        .height(60.dp)
                        .width(280.dp),
                    value = viewModel.newRule,
                    label = { Text(text = "Text") },
                    onValueChange = { viewModel.newRule = it },
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if(viewModel.newRule.equals("")){
                                Toast.makeText(context, "Please input rule!", Toast.LENGTH_LONG).show()
                            } else {
                                viewModel.createRule()
                                openCreateRuleDialog.value = false
                                Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
                            }
                        }
                    ) {
                        Text("Create")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { openCreateRuleDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }

    //TODO: Edit rule
    if (openEditRuleDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openEditRuleDialog.value = false
            },
            backgroundColor = Color.Gray,
            title = {
                Text(text = "Edit rule")
            },
            text = {
                OutlinedTextField(
                    modifier = Modifier
                        .height(60.dp)
                        .width(280.dp),
                    value = viewModel.editRule,
                    label = { Text(text = "Text") },
                    onValueChange = { viewModel.editRule = it },
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.updateRule(ruleIdToEdit.value)
                            openEditRuleDialog.value = false
                            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
                        }
                    ) {
                        Text("Save")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { openEditRuleDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }
    //TODO: Create flair

    if (openCreateFlairDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openCreateFlairDialog.value = false
            },
            backgroundColor = Color.Gray,
            title = {
                Text(text = "New flair")
            },
            text = {
                OutlinedTextField(
                    modifier = Modifier
                        .height(60.dp)
                        .width(280.dp),
                    value = viewModel.newFlair,
                    label = { Text(text = "Text") },
                    onValueChange = { viewModel.newFlair = it },
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if (viewModel.newFlair.equals("")){
                                Toast.makeText(context, "Please input flair!", Toast.LENGTH_LONG).show()
                            } else {
                                viewModel.createFlair()
                                openCreateFlairDialog.value = false
                                Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
                            }
                        }
                    ) {
                        Text("Create")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { openCreateFlairDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }

    //TODO: Edit flair

    if (openEditFlairDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openEditFlairDialog.value = false
            },
            backgroundColor = Color.Gray,
            title = {
                Text(text = "Edit rule")
            },
            text = {
                OutlinedTextField(
                    modifier = Modifier
                        .height(60.dp)
                        .width(280.dp),
                    value = viewModel.editFlair,
                    label = { Text(text = "Text") },
                    onValueChange = { viewModel.editFlair = it },
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.updateFlair(flairIdToEdit.value)
                            openEditFlairDialog.value = false
                            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()

                        }
                    ) {
                        Text("Save")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { openEditFlairDialog.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }
}