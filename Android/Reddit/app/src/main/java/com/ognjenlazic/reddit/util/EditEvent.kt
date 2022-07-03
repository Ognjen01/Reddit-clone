package com.ognjenlazic.reddit.util

import androidx.compose.ui.focus.FocusState

sealed class EditEvent{
    data class EnteredTitle(val value: String): EditEvent()
    data class EnteredText(val value: String): EditEvent()
}
