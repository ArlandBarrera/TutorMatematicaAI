package com.example.tutormatematicaai.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tutormatematicaai.R
import com.example.tutormatematicaai.OpenAIService
import com.example.tutormatematicaai.PermissionHelper
import com.example.tutormatematicaai.SpeechRecognizerHelper
import com.example.tutormatematicaai.TextToSpeechHelper
import com.example.tutormatematicaai.utils.ApiValues
import com.example.tutormatematicaai.utils.Prompts

class NumerosNaturalesActivity : AppCompatActivity() {

  private lateinit var textToSpeechHelper: TextToSpeechHelper
  private lateinit var speechRecognizerHelper: SpeechRecognizerHelper
  private lateinit var textViewPrompt: TextView
  private lateinit var openAIService: OpenAIService

  // Código de permiso de grabación de audio
  private val REQUEST_RECORD_AUDIO_PERMISSION_CODE = 2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_numeros_naturales)

    // Botón para volver al menú principal
    val btnVolverMenu = findViewById<Button>(R.id.btnVolverMenu)
    btnVolverMenu.setOnClickListener {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }

    // Inicializar TextToSpeech y SpeechRecognizer
    textToSpeechHelper = TextToSpeechHelper(this)
    speechRecognizerHelper = SpeechRecognizerHelper(this) { userSpeech ->
      // Enviar lo que el usuario dijo a OpenAI
      processUserSpeech(userSpeech)
    }

    // Inicializar OpenAIService
    openAIService = OpenAIService(ApiValues.API_KEY)

    // Inicializar el TextView
    textViewPrompt = findViewById(R.id.tvPrompt)

    // Generar el primer prompt automáticamente usando Prompts.NUMEROS_NATURALES
    generateAndSpeakPrompt(Prompts.NUMEROS_NATURALES)
  }

  private fun generateAndSpeakPrompt(prompt: String) {
    // Llamar a la API de OpenAI con el prompt de números naturales
    openAIService.generateText(prompt) { generatedText ->
      // Mostrar el texto generado en el TextView
      textViewPrompt.text = generatedText

      // Pasar el texto generado a Text-to-Speech para que lo lea en voz alta
      textToSpeechHelper.speak(generatedText)

      // Después de leer el prompt, esperar la respuesta del usuario por voz
      listenToUser()
    }
  }

  private fun listenToUser() {
    if (PermissionHelper.checkAndRequestPermission(
        this, Manifest.permission.RECORD_AUDIO, REQUEST_RECORD_AUDIO_PERMISSION_CODE
      )) {
      speechRecognizerHelper.startListening()
    }
  }

  private fun processUserSpeech(userSpeech: String) {
    // Enviar la respuesta del usuario como prompt al modelo de OpenAI
    openAIService.generateText(userSpeech) { generatedResponse ->
      // Respuesta generada por la API, se reproduce con Text-to-Speech
      textToSpeechHelper.speak(generatedResponse)

      // Luego de dar la respuesta, seguir esperando al usuario
      listenToUser()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    textToSpeechHelper.stop()
    speechRecognizerHelper.destroy()
  }

  override fun onRequestPermissionsResult(
    requestCode: Int, permissions: Array<out String>, grantResults: IntArray
  ) {
    if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION_CODE) {
      if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        textViewPrompt.text = "Permiso de grabación denegado"
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }
}
