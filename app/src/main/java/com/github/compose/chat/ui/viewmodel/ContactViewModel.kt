package com.github.compose.chat.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.github.compose.chat.data.model.uistate.ContactUiState
import com.github.compose.chat.data.source.DbManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val dbManager: DbManager
) : ViewModel() {
    var uiState by mutableStateOf(ContactUiState())
        private set

    fun loadContacts() {
        updateState(
            uiState.copy(
                loading = true
            )
        )
        dbManager.getUserContacts(
            onSuccess = {
                updateState(
                    uiState.copy(
                        loading = false,
                        contacts = it.toMutableStateList()
                    )
                )
            },
            onFailure = {
                updateState(
                    uiState.copy(
                        loading = false,
                        hasError = true,
                        error = it
                    )
                )
            }
        )
    }

    fun updateState(state: ContactUiState) {
        this.uiState = state
    }
}