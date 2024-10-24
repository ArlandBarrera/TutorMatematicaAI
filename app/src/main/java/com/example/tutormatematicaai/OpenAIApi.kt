package com.example.tutormatematicaai

import com.example.tutormatematicaai.utils.ApiValues
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// Estructura del mensaje requerida por el modelo gpt-3.5-turbo
data class Message(val role: String, val content: String)

data class OpenAIChatRequest(val model: String, val messages: List<Message>, val max_tokens: Int)
data class OpenAIChatResponse(val choices: List<Choice>)
data class Choice(val message: Message)

interface OpenAIApi {
  @Headers("Content-Type: application/json")
  @POST(ApiValues.COMPLETION_URL)  // Completar url /chat/completions
  fun generateChat(@Body request: OpenAIChatRequest): Call<OpenAIChatResponse>
}