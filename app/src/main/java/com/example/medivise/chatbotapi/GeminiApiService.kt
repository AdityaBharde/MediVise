package com.example.medivise.chatbotapi

import com.example.medivise.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

object GeminiApiService {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )
    suspend fun getGenerativeResponse(prompt: String): String {
        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Error: Could not get a response."
        } catch (e: Exception) {
            e.printStackTrace()
            "Error: ${e.message}"
        }
    }
}
