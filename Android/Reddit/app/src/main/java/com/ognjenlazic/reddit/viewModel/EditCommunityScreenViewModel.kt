package com.ognjenlazic.reddit.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.reddit.model.Community
import com.ognjenlazic.reddit.model.Flair
import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.CommunityBody
import com.ognjenlazic.reddit.network.model.FlairCommunityRelation
import com.ognjenlazic.reddit.network.model.PostBody
import com.ognjenlazic.reddit.network.model.SingleRule
import kotlinx.coroutines.launch

class EditCommunityScreenViewModel: ViewModel() {

    var community by mutableStateOf<Community>(Community())
        private set

    var communityId by mutableStateOf(0)
    var infoIsLoaded: Boolean by mutableStateOf(false)
    var errorMessage: String by mutableStateOf("")
    var communityName: String by mutableStateOf("")
    var communityDesc: String by mutableStateOf("")
    var newRule: String by mutableStateOf("")
    var newFlair: String by mutableStateOf("")
    var editRule: String by mutableStateOf("")
    var editFlair: String by mutableStateOf("")
    var flairList:List<Flair> by mutableStateOf(listOf())

    fun getCommunity(){
        if(!infoIsLoaded){
            viewModelScope.launch {
                try {
                    community = RetrofitInstance.community.getCommunityById(communityId)
                    communityName = community.name
                    communityDesc = community.description
                    infoIsLoaded = true
                }
                catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
    }

    fun getCommunityRules(){

    }

    fun getCommunityFlairs(){
        viewModelScope.launch {
            try {
                flairList = RetrofitInstance.flair.getCommunityFlairs(communityId = communityId)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun createRule(){
        viewModelScope.launch {
            var rule = SingleRule(communityId, newRule, 10)
            try {
                val response = RetrofitInstance.rule.createRule(rule)
                Log.d("New rule" , response.toString())
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Rule", e.message.toString())
            }
        }
    }

    fun updateRule(ruleId: Int){
        viewModelScope.launch {
            var rule = SingleRule(communityId, editRule, ruleId)
            try {
                val response = RetrofitInstance.rule.createRule(rule)
                Log.d("Edit rule" , response.toString())
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Rule", e.message.toString())
            }
        }
    }

    fun deleteRule(ruleId: Int){
        viewModelScope.launch {
            var rule = SingleRule(communityId, editRule, ruleId)
            try {
                RetrofitInstance.rule.deleteRule(ruleId)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Rule", e.message.toString())
            }
        }
    }

    fun createFlair(){
        viewModelScope.launch {
            var flair = Flair(10, newFlair)
            try {
                val response = RetrofitInstance.flair.createFlair(flair)
                RetrofitInstance.flair.createFlairCommunityRelation(FlairCommunityRelation(communityId, response.flairId))
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("FLairErr", e.message.toString())
            }
        }
    }

    fun updateFlair(flairId: Int){
        viewModelScope.launch {
            var flair = Flair(flairId, editFlair)
            try {
                val response = RetrofitInstance.flair.updateFlair(flair)
                Log.d("Edit flair" , response.toString())
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Rule", e.message.toString())
            }
        }
    }

    fun deleteFlair(flairId: Int){
        viewModelScope.launch {
            try {
                RetrofitInstance.flair.deleteFlair(flairId)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Rule", e.message.toString())
            }
        }
    }

    fun saveCommunityInformation(){
        viewModelScope.launch {
            try {
                var communityBody = CommunityBody(communityId, "", communityDesc, community.moderatorId, communityName, null, false, null)
                var communityResponse = RetrofitInstance.community.saveCommunity(communityBody)
                Log.d("CommunityUpdate", communityResponse.toString())
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error", e.message.toString())
            }
        }
    }
}