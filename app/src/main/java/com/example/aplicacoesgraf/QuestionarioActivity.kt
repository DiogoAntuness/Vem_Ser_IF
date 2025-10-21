package com.example.aplicacoesgraf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.aplicacoesgraf.databinding.ActivityQuestionarioBinding

class QuestionarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionarioBinding
    private val viewModel: QuestionarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Listener para o botão DENTRO do card de instruções
        binding.buttonStartQuiz.setOnClickListener {
            viewModel.iniciarQuizDeFato()
        }

        binding.buttonNext.setOnClickListener {
            val resposta = binding.sliderAnswer.value.toInt()
            viewModel.processarEAvancar(resposta)
        }

        binding.buttonExit.setOnClickListener {
            mostrarDialogoSair()
        }
    }

    private fun setupObservers() {
        // Observador para mostrar/esconder as instruções
        viewModel.mostrarInstrucoes.observe(this) { mostrar ->
            if (mostrar) {
                binding.cardInstrucoes.visibility = View.VISIBLE
                binding.groupQuestionario.visibility = View.GONE
            } else {
                binding.cardInstrucoes.visibility = View.GONE
                binding.groupQuestionario.visibility = View.VISIBLE
            }
        }

        viewModel.perguntaAtual.observe(this) { pergunta ->
            binding.textViewQuestion.text = pergunta.texto
            binding.sliderAnswer.value = 1.0f // Reseta o slider para a posição inicial
        }

        viewModel.progresso.observe(this) { progresso ->
            binding.progressBar.progress = progresso
        }

        viewModel.questionarioFinalizado.observe(this) { finalizado ->
            // Adicionamos 'finalizado == true' para evitar acionamentos múltiplos
            if (finalizado == true) {
                finalizarQuestionario()
            }
        }
    }

    private fun finalizarQuestionario() {
        val resultado = viewModel.resultadoFinal.value ?: return
        val intent = Intent(this, ResultadoActivity::class.java).apply {
            putExtra(ResultadoActivity.PONTUACOES_FINAIS_KEY, HashMap(resultado))
        }
        startActivity(intent)
        finish()
    }

    private fun mostrarDialogoSair() {
        AlertDialog.Builder(this)
            .setTitle("Sair do Questionário")
            .setMessage("Tem certeza que deseja sair? Todo o seu progresso será perdido.")
            .setPositiveButton("Sim, Sair") { _, _ ->
                finish() // Fecha a tela do questionário e volta para a principal
            }
            .setNegativeButton("Cancelar", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}

