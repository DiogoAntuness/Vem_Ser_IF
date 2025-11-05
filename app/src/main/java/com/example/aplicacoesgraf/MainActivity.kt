package com.example.aplicacoesgraf
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aplicacoesgraf.databinding.ActivityMainBinding // Certifique-se de que o nome do pacote está correto

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonIniciarTeste.setOnClickListener {
            // Inicia a QuestionarioActivity
            val intent = Intent(this, QuestionarioActivity::class.java)
            startActivity(intent)
        }

        binding.buttonGraduacoes.setOnClickListener {
            val intent = Intent(this, GraduacoesActivity::class.java)
            startActivity(intent)
        }

        binding.btnCreditos.setOnClickListener {
            val intent = Intent(this, CreditosActivity::class.java)
            startActivity(intent)
        }
    }
}
