package com.example.aplicacoesgraf

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplicacoesgraf.databinding.ActivityResultadoBinding
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

// A definição de 'Curso' agora vem do arquivo centralizado QuizModel.kt

class ResultadoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoBinding

    companion object {
        const val PONTUACOES_FINAIS_KEY = "PONTUACOES_FINAIS"
    }

    private val descrisaoCurso = mapOf(
        Cursoo.LCC to listOf(
            "Este curso é para pessoas criativas e com raciocínio lógico, que gostam de resolver problemas e criar novas tecnologias para o futuro.",
            "Este curso é para pessoas interessadas em ensinar e aprender com o uso das tecnologias. Indicado para quem tem perfil investigativo, gosta de trabalhar em equipe e quer contribuir com a inclusão digital na sociedade.",
            "Este curso é para pessoas interessadas em ensinar e aprender com o uso das tecnologias. Também é para quem acredita que a tecnologia pode inspirar o aprendizado e melhorar a qualidade da educação."
        ),
        Cursoo.LCA to listOf(
            "Ideal para quem se conecta com a natureza e tem interesse em otimizar a produção de alimentos de forma sustentável, cuidando do meio ambiente.",
            "Seu perfil indica uma vocação para aliar tecnologia e natureza, buscando soluções inovadoras para o campo e o agronegócio.",
            "Aqui você aprenderá a gerenciar recursos naturais de forma inteligente, garantindo a segurança alimentar e a sustentabilidade do planeta."
        ),
        Cursoo.LQI to listOf(
            "Para mentes curiosas e investigativas que buscam entender as transformações da matéria e criar novos materiais para a indústria e a ciência.",
            "Sua curiosidade pelas reações que formam o mundo ao nosso redor é a chave para uma carreira de descobertas em laboratório.",
            "Transforme sua paixão por experimentos em uma profissão, desenvolvendo produtos e soluções que melhoram a vida das pessoas."
        ),
        Cursoo.ADM to listOf(
            "Voltado para líderes natos com visão estratégica, que gostam de organizar, planejar e gerenciar recursos para alcançar grandes objetivos.",
            "Sua habilidade de organizar e tomar decisões é perfeita para o mundo dos negócios, onde você poderá gerenciar equipes e projetos de sucesso.",
            "Se você sonha em empreender, otimizar processos ou liderar uma grande empresa, a Administração te dará as ferramentas para chegar lá."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("UNCHECKED_CAST")
        val finalScores = intent.getSerializableExtra(PONTUACOES_FINAIS_KEY) as? HashMap<Cursoo, Int>

        if (finalScores != null) {
            setupResultado(finalScores)
            setupChart(finalScores)
        }

        binding.bntVoltarInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun setupResultado(scores: HashMap<Cursoo, Int>) {
        val maxScore = scores.values.maxOrNull() ?: 0
        val topCourses = scores.filter { it.value == maxScore }.keys
        val priorityOrder = listOf(Cursoo.LCC, Cursoo.LQI, Cursoo.ADM, Cursoo.LCA)
        val winner = priorityOrder.firstOrNull { it in topCourses }

        if (winner != null) {
            binding.textViewResultadoTitulo.text = "Seu curso mais provável é:"
            binding.textViewResultadoCursoNome.text = getCourseFullName(winner)
            // --- MUDANÇA 2: Lógica de seleção aleatória ---
            // Pega a lista de descrições para o curso vencedor
            val descricoesPossiveis = descrisaoCurso[winner]
            // Sorteia uma das descrições da lista e a exibe.
            // O '?.' garante que o app não quebre se a lista for nula.
            binding.textViewResultadoDescricao.text = descricoesPossiveis?.random()
        }
    }

    private fun setupChart(scores: HashMap<Cursoo, Int>) {
        val entries = ArrayList<RadarEntry>()
        val labels = ArrayList<String>()
        val courseOrder = listOf(Cursoo.LCC, Cursoo.LQI, Cursoo.ADM, Cursoo.LCA)

        courseOrder.forEach { curso ->
            entries.add(RadarEntry(scores[curso]?.toFloat() ?: 0f))
            labels.add(curso.name)
        }

        val dataSet = RadarDataSet(entries, "Pontuação").apply {
            color = Color.parseColor("#004D00") // Verde IF Baiano
            fillColor = Color.parseColor("#004D00")
            setDrawFilled(true)
            fillAlpha = 180
            lineWidth = 2f
            isDrawHighlightCircleEnabled = true
            setDrawHighlightIndicators(false)
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        binding.radarChart.apply {
            data = RadarData(dataSet)
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.textSize = 14f
            xAxis.textColor = Color.BLACK
            yAxis.setLabelCount(5, true)
            yAxis.axisMinimum = 0f
            yAxis.setDrawLabels(false)
            description.isEnabled = false
            legend.isEnabled = false
            animateXY(1400, 1400)
            invalidate()
        }
    }

    private fun getCourseFullName(curso: Cursoo): String {
        return when (curso) {
            Cursoo.LCC -> "Lic. em Ciências da Computação"
            Cursoo.LCA -> "Lic. em Ciências Agrárias"
            Cursoo.LQI -> "Lic. em Química"
            Cursoo.ADM -> "Administração"
        }
    }
}