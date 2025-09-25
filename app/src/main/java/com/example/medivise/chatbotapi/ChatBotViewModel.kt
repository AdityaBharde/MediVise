package com.example.medivise.chatbotapi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.medivise.database.ChatMessage
import com.example.medivise.database.ConversationDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ChatBotViewModel(application: Application) : AndroidViewModel(application) {

    private val chatMessageDao = ConversationDatabase.getDatabase(application).chatMessageDao()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _uiState = MutableStateFlow<ChatBotUiState>(ChatBotUiState.Initial)
    val uiState: StateFlow<ChatBotUiState> = _uiState

    init {
        chatMessageDao.getAllMessages().onEach { chatMessages ->
            _messages.value = chatMessages
        }.launchIn(viewModelScope)
    }

    fun sendMessage(userInput: String) {
        viewModelScope.launch {
            val userMessage = ChatMessage(text = userInput, sender = "user")
            chatMessageDao.insertMessage(userMessage)

            _uiState.value = ChatBotUiState.Loading

            val prompt = """
                You are a caring, supportive mental health assistant for an app called MediVise.
                Based on this "$userInput" interact with the user and provide solution.
                Try to solve the problem be empathetic , nice. 
            """.trimIndent()

            val botResponseText = GeminiApiService.getGenerativeResponse(prompt)

            val botMessage = ChatMessage(text = botResponseText, sender = "bot")
            chatMessageDao.insertMessage(botMessage)

            _uiState.value = ChatBotUiState.Success(botResponseText)
        }
    }
}

sealed interface ChatBotUiState {
    object Initial : ChatBotUiState
    object Loading : ChatBotUiState
    data class Success(val responseText: String) : ChatBotUiState
    data class Error(val errorMessage: String) : ChatBotUiState
}