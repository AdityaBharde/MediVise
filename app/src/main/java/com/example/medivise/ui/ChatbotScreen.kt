package com.example.medivise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medivise.chatbotapi.ChatBotUiState
import com.example.medivise.chatbotapi.ChatBotViewModel
import com.example.medivise.database.ChatMessage

@Composable
fun ChatBotScreen() {
    val chatBotViewModel: ChatBotViewModel = viewModel()
    val messages by chatBotViewModel.messages.collectAsStateWithLifecycle()
    val uiState by chatBotViewModel.uiState.collectAsStateWithLifecycle()
    var userInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(Modifier.height(50.dp))
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                ChatBubble(message = message)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("How are you feeling?") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (userInput.isNotBlank()) {
                        chatBotViewModel.sendMessage(userInput)
                        userInput = ""
                    }
                },
                enabled = uiState !is ChatBotUiState.Loading
            ) {
                if (uiState is ChatBotUiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Message")
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isUserMessage = message.sender == "user"
    val bubbleColor = if (isUserMessage) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val horizontalAlignment = if (isUserMessage) Alignment.End else Alignment.Start


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (isUserMessage) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = message.text,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(bubbleColor)
                .padding(12.dp)
                .widthIn(max = 280.dp), // Max width for bubbles
            style = MaterialTheme.typography.bodyLarge
        )
    }
}