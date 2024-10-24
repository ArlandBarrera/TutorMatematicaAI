package com.example.tutormatematicaai

import android.util.Log
import com.example.tutormatematicaai.utils.ApiValues
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenAIService(private val apiKey: String) {
  private val api = RetrofitClient.instance.create(OpenAIApi::class.java)

  fun generateText(prompt: String, onResult: (String) -> Unit) {
    // Crear el mensaje como rol "user"
    val messages = listOf(Message("user", prompt))

    // Crear la solicitud con el modelo gpt-3.5-turbo
    val request = OpenAIChatRequest(ApiValues.API_MODEL, messages, ApiValues.MAX_TOKENS)
    val call = api.generateChat(request)

    call.enqueue(object : Callback<OpenAIChatResponse> {
      override fun onResponse(call: Call<OpenAIChatResponse>, response: Response<OpenAIChatResponse>) {
        if (response.isSuccessful) {
          // Obtener el texto de la respuesta
          val text = response.body()?.choices?.get(0)?.message?.content?.trim() ?: "No response"
          onResult(text)
        } else {
          Log.e("OpenAI", "API Error: ${response.code()} - ${response.message()}")
          onResult("Error in API response: ${response.code()} - ${response.message()}")
        }
      }

      override fun onFailure(call: Call<OpenAIChatResponse>, t: Throwable) {
        Log.e("OpenAI", "Request failed: ${t.message}", t)
        onResult("Request failed: ${t.message}")
      }
    })
  }
}