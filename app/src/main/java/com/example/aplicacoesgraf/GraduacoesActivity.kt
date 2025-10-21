package com.example.aplicacoesgraf

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.aplicacoesgraf.databinding.ActivityGraduacoesBinding

class GraduacoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraduacoesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraduacoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura os cliques para os links de Ciências da Computação
        setupLink(binding.linkLccPagina, "https://www.ifbaiano.edu.br/unidades/bonfim/licenciatura-em-ciencias-da-computacao/")
        setupLink(binding.linkLccMatriz, "https://www.ifbaiano.edu.br/unidades/bonfim/files/2014/03/matriz-2016.pdf")
        setupLink(binding.linkLccInsta, "https://www.instagram.com/algoritmojovem/")

        // Configura os cliques para os links de Ciências Ágrarias
        setupLink(binding.linkLcaPagina, "https://www.ifbaiano.edu.br/unidades/bonfim/licenciatura-em-ciencias-agrarias/")
        setupLink(binding.linkLcaMatriz, "https://www.ifbaiano.edu.br/unidades/bonfim/files/2022/05/PPC-LICA-2021.pdf")
        setupLink(binding.linkLcaInsta, "https://www.instagram.com/ca.agrariasbonfim/")

        // Configura os cliques para os links de Química
        setupLink(binding.linkLqPagina, "https://www.ifbaiano.edu.br/unidades/bonfim/licenciatura-em-quimica/")
        setupLink(binding.linkLqMatriz, "https://drive.google.com/file/d/13dRJntGGA1TE-Z4GRtcR-lMoF78XUYPt/view")
        setupLink(binding.linkLqInsta, "https://www.instagram.com/quimicaifbaianosb/")

        // Configura os cliques para os links de ADM
        setupLink(binding.linkAdmPagina, "https://www.ifbaiano.edu.br/unidades/bonfim/bacharelado-em-administracao/")
        setupLink(binding.linkAdmMatriz, "https://drive.google.com/file/d/1UnCuZU86EgZMHpth8aEvKcka81OGq2FA/view")
        setupLink(binding.linkAdmInsta, "https://www.instagram.com/adm.ifbonfim/")

        // Configura o clique do botão Voltar
        binding.buttonBackToMain.setOnClickListener {
            finish() // Simplesmente fecha a tela atual, voltando para a anterior (MainActivity)
        }
    }

    /**
     * Função auxiliar para configurar um TextView como um link que abre no navegador.
     * @param textView O componente de texto que será clicável.
     * @param url A URL que deve ser aberta ao clicar.
     */
    private fun setupLink(textView: TextView, url: String) {
        textView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}
