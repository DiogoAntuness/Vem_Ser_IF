package com.example.aplicacoesgraf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplicacoesgraf.databinding.ActivityCreditosBinding

class CreditosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreditosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o clique do botão Voltar
        binding.buttonBack.setOnClickListener {
            finish() // Fecha a tela de créditos e volta para a anterior
        }
    }
}
