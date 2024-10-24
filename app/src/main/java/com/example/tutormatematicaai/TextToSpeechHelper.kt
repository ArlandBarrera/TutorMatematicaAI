package com.example.tutormatematicaai

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

class TextToSpeechHelper(context: Context, private val locale: Locale = Locale("es", "ES")) : TextToSpeech.OnInitListener {
  private var textToSpeech: TextToSpeech = TextToSpeech(context, this)

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      val result = textToSpeech.setLanguage(locale)
      if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        Log.e("TTS", "Idioma no soportado")
      }
    } else {
      Log.e("TTS", "Inicialización fallida")
    }
  }

  fun speak(text: String) {
    if (text.isNotEmpty()) {
      textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
  }

  fun stop() {
    textToSpeech.stop()
    textToSpeech.shutdown()
  }
}