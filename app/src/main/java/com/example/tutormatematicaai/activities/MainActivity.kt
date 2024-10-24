package com.example.tutormatematicaai.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.widget.Button
import com.example.tutormatematicaai.R

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // SplashScreen
    installSplashScreen()
    // Layout main
    setContentView(R.layout.activity_main)
    // Layout menu principal
    setContentView(R.layout.menu_principal)

    // Boton para cambiar a pantalla Numeros Naturales
    val btnNumerosNaturales = findViewById<Button>(R.id.btnMenuNumerosNaturales)
    btnNumerosNaturales.setOnClickListener(){
      val intent = Intent(this, NumerosNaturalesActivity::class.java)
      startActivity(intent)
    }

    // Boton para cambiar a pantalla Operacion Basicas
    val btnOperacionesBasicas = findViewById<Button>(R.id.btnMenuOperacionesBasicas)
    btnOperacionesBasicas.setOnClickListener(){
      val intent = Intent(this, OperacionesBasicasActivity::class.java)
      startActivity(intent)
    }
  }
}