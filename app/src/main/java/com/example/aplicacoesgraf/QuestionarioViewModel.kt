package com.example.aplicacoesgraf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionarioViewModel : ViewModel() {

    // --- LiveData para controlar a visibilidade das instruções ---
    private val _mostrarInstrucoes = MutableLiveData<Boolean>(true)
    val mostrarInstrucoes: LiveData<Boolean> get() = _mostrarInstrucoes

    private val _perguntaAtual = MutableLiveData<Pergunta>()
    val perguntaAtual: LiveData<Pergunta> get() = _perguntaAtual

    private val _progresso = MutableLiveData<Int>()
    val progresso: LiveData<Int> get() = _progresso

    private val _questionarioFinalizado = MutableLiveData<Boolean>(false)
    val questionarioFinalizado: LiveData<Boolean> get() = _questionarioFinalizado

    private val _resultadoFinal = MutableLiveData<Map<Cursoo, Int>>()
    val resultadoFinal: LiveData<Map<Cursoo, Int>> get() = _resultadoFinal

    private lateinit var listaDePerguntas: List<Pergunta>
    private var perguntaAtualIndex = 0
    private val pontuacoesFinais = mutableMapOf<Cursoo, Int>()

    init {
        // Apenas carrega as perguntas, não inicia o questionário ainda
        carregarPerguntas()
    }

    // Chamado quando o usuário clica em "Entendi, começar!"
    fun iniciarQuizDeFato() {
        _mostrarInstrucoes.value = false // Esconde as instruções
        iniciarQuestionario() // Agora sim, inicia o teste
    }

    private fun iniciarQuestionario() {
        Cursoo.values().forEach { pontuacoesFinais[it] = 0 }
        perguntaAtualIndex = 0
        _questionarioFinalizado.value = false
        atualizarUI()
    }

    fun processarEAvancar(valorResposta: Int) {
        if (perguntaAtualIndex < listaDePerguntas.size) {
            val pergunta = listaDePerguntas[perguntaAtualIndex]
            val regra = pergunta.regras.entries.find { valorResposta in it.key }
            regra?.let {
                pontuacoesFinais[Cursoo.LCC] = pontuacoesFinais.getValue(Cursoo.LCC) + it.value.lcc
                pontuacoesFinais[Cursoo.LCA] = pontuacoesFinais.getValue(Cursoo.LCA) + it.value.lca
                pontuacoesFinais[Cursoo.LQI] = pontuacoesFinais.getValue(Cursoo.LQI) + it.value.lq
                pontuacoesFinais[Cursoo.ADM] = pontuacoesFinais.getValue(Cursoo.ADM) + it.value.adm
            }
        }

        perguntaAtualIndex++

        if (perguntaAtualIndex < listaDePerguntas.size) {
            atualizarUI()
        } else {
            _resultadoFinal.value = pontuacoesFinais
            _questionarioFinalizado.value = true
        }
    }

    private fun atualizarUI() {
        _perguntaAtual.value = listaDePerguntas[perguntaAtualIndex]
        _progresso.value = ((perguntaAtualIndex) * 100) / listaDePerguntas.size
    }

    private fun carregarPerguntas() {
        val p01 = Pergunta(//expecifica de: LCC
            texto = "Gosto de desmontar e entender como funcionam dispositivos eletrônicos ou sistemas.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 4, lq = 2),
                7..8  to Pontuacao(lcc = 3, lq = 1),
                5..6  to Pontuacao(lcc = 2),
                3..4  to Pontuacao(adm = 1, lca = 1),
                1..2  to Pontuacao(lca = 2, adm = 2)
            )
        )
        val p02 = Pergunta(//expecifica de: LCC
            texto = "Tenho interesse em desenvolver soluções que ajudem a automatizar processos de trabalho.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 4),
                7..8  to Pontuacao(lcc = 3),
                5..6  to Pontuacao(lcc = 2),
                3..4  to Pontuacao(adm = 1, lca = 1, lq = 1),
                1..2  to Pontuacao(lca = 2, adm = 2, lq = 2)
            )
        )
        val p03 = Pergunta(//expecifica de: LQ
            texto = "Gosto de ler e pesquisar sobre composição de materiais, fórmulas e reações químicas.",
            regras = mapOf(
                9..10 to Pontuacao(lq = 4, lca = 1),
                7..8  to Pontuacao(lq = 3, lca = 1, lcc = 1),
                5..6  to Pontuacao(lq = 2, lcc = 1,),
                3..4  to Pontuacao(adm = 1),
                1..2  to Pontuacao(adm = 2)
            )
        )
        val p04 = Pergunta(//expecifica de: LQ
            texto = "Sinto curiosidade sobre como os elementos interagem e as transformações da matéria.",
            regras = mapOf(
                9..10 to Pontuacao(lq = 4),
                7..8  to Pontuacao(lq = 3),
                5..6  to Pontuacao(lq = 2),
                3..4  to Pontuacao(lca = 1, lcc = 1, adm = 1,),
                1..2  to Pontuacao(adm = 2, lca = 2, lcc = 2)
            )
        )
        val p05 = Pergunta(//expecifica de: LCA
            texto = "Gosto de aprender sobre solos, clima e métodos eficientes de cultivo ou criação.",
            regras = mapOf(
                9..10 to Pontuacao(lca = 4, lq = 2),
                7..8  to Pontuacao(lca = 3, lq = 1),
                5..6  to Pontuacao(lca = 2, lq = 1),
                3..4  to Pontuacao(lca = 1, lcc = 1, adm = 1,),
                1..2  to Pontuacao(adm = 2, lcc = 2)
            )
        )
        val p06 = Pergunta(//expecifica de: LCA
            texto = "É importante para mim que meu trabalho seja realizado principalmente em ambientes externos (campo, natureza).",
            regras = mapOf(
                9..10 to Pontuacao(lca = 4),
                7..8  to Pontuacao(lca = 3),
                5..6  to Pontuacao(lca = 2),
                3..4  to Pontuacao(lcc = 2, adm = 1, lq = 1),
                1..2  to Pontuacao(adm = 2, lcc = 3, lq = 2)
            )
        )
        val p07 = Pergunta(//expecifica de: ADM
            texto = "Me interesso por temas como economia, finanças e o funcionamento do mercado de trabalho.",
            regras = mapOf(
                9..10 to Pontuacao(adm = 4),
                7..8  to Pontuacao(adm = 3),
                5..6  to Pontuacao(adm= 2),
                3..4  to Pontuacao(lca = 1, lcc = 1, lq = 1),
                1..2  to Pontuacao(lca = 2, lcc = 2, lq = 2)
            )
        )
        val p08 = Pergunta(//expecifica de: ADM
            texto = "Me sinto à vontade negociando ou convencendo pessoas de uma ideia ou proposta.",
            regras = mapOf(
                9..10 to Pontuacao(adm = 4, lcc = 2),
                7..8  to Pontuacao(adm = 3, lcc = 1),
                5..6  to Pontuacao(adm= 2, lcc = 1),
                3..4  to Pontuacao(lca = 1, lq = 1),
                1..2  to Pontuacao(lca = 2, lq = 2)
            )
        )
        val p09 = Pergunta(//expecifica de: ADM
            texto = "Consigo tomar decisões rapidamente em situações de alta pressão ou crise.",
            regras = mapOf(
                9..10 to Pontuacao(adm = 4, lca = 2),
                7..8  to Pontuacao(adm = 3, lca = 1),
                5..6  to Pontuacao(adm= 2),
                3..4  to Pontuacao(lcc = 1, adm = 1, lq = 1),
                1..2  to Pontuacao(lcc = 2, lq = 3)
            )
        )
        val p10 = Pergunta(//GERAL
            texto = "Tenho facilidade em lidar com números, lógica e resolver problemas passo a passo.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 4, adm = 3, lq = 3),
                7..8  to Pontuacao(lcc = 3, adm = 2, lq = 3),
                5..6  to Pontuacao(lcc = 2, adm = 2, lq = 2, lca = 1),
                3..4  to Pontuacao(lcc = 1, adm = 1, lq = 1, lca = 2),
                1..2  to Pontuacao(lca = 3)
            )
        )
        val p11 = Pergunta(//GERAL
            texto = "Consigo concentrar-me por longos períodos em tarefas que exigem detalhe e precisão.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 3, adm = 3, lq = 4),
                7..8  to Pontuacao(lcc = 2, adm = 2, lq = 3),
                5..6  to Pontuacao(lcc = 1, adm = 2, lq = 2, lca = 1),
                3..4  to Pontuacao(lcc = 1, adm = 2, lca = 2),
                1..2  to Pontuacao(lca = 3)
            )
        )
        val p12 = Pergunta(//GERAL
            texto = "Se estivesse em uma equipe, gostaria de ser a pessoa responsável por documentar, revisar e testar a qualidade",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 4, adm = 1, lq = 4),
                7..8  to Pontuacao(lcc = 3, adm = 1, lq = 3),
                5..6  to Pontuacao(lcc = 2, adm = 2, lq = 2),
                3..4  to Pontuacao(lcc = 1, lca = 2),
                1..2  to Pontuacao(lca = 3)
            )
        )
        val p13 = Pergunta(//GERAL
            texto = "Tenho aptidão para organizar, classificar e categorizar grandes volumes de informação.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 3, adm = 4),
                7..8  to Pontuacao(lcc = 2, adm = 3, lq = 1,),
                5..6  to Pontuacao(lcc = 1, adm = 1, lq = 2, lca = 2),
                3..4  to Pontuacao(lca = 2, lq = 2),
                1..2  to Pontuacao(lca = 3)
            )
        )
        val p14 = Pergunta(//GERAL
            texto = "Consigo aprender a usar ferramentas e equipamentos novos rapidamente.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 3, lq = 3, lca = 3),
                7..8  to Pontuacao(lcc = 2, lq = 2, lca = 2),
                5..6  to Pontuacao(lcc = 1, adm = 1, lq = 1, lca = 1),
                3..4  to Pontuacao(adm = 2),
                1..2  to Pontuacao(adm = 3)
            )
        )
        val p15 = Pergunta(//GERAL
            texto = "Dou muito valor à pesquisa científica e à busca por novas descobertas para a humanidade.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 2, lq = 4, lca = 3),
                7..8  to Pontuacao(lcc = 2, lq = 3, lca = 2),
                5..6  to Pontuacao(adm = 2),
                3..4  to Pontuacao(adm = 3),
                1..2  to Pontuacao(adm = 4)
            )
        )
        val p16 = Pergunta(//GERAL
            texto = "Prefiro trabalhar em um escritório ou laboratório com pouca interação social direta.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 3, lq = 3),
                7..8  to Pontuacao(lcc = 2, lq = 2),
                5..6  to Pontuacao(lcc = 1, adm = 1, lq = 1, lca = 1),
                3..4  to Pontuacao(adm = 2, lca = 2),
                1..2  to Pontuacao(adm = 3, lca = 2)
            )
        )
        val p17 = Pergunta(//PEDAGOGICA
            texto = "Tenho paciência e prazer em explicar conceitos difíceis a outras pessoas.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 4, lq = 4, lca = 4),
                7..8  to Pontuacao(lcc = 3, lq = 3, lca = 3),
                5..6  to Pontuacao(lcc = 2, adm = 1, lq = 2, lca = 2),
                3..4  to Pontuacao(adm = 2),
                1..2  to Pontuacao(adm = 3)
            )
        )
        val p18 = Pergunta(//PEDAGOGICA
            texto = "Considero importante participar da formação de jovens e atuar em ambiente escolar.",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 4, lq = 4, lca = 4),
                7..8  to Pontuacao(lcc = 3, lq = 3, lca = 3),
                5..6  to Pontuacao(lcc = 2, adm = 2, lq = 2, lca = 2),
                3..4  to Pontuacao(adm = 3),
                1..2  to Pontuacao(adm = 4)
            )
        )
        val p19 = Pergunta(//PEDAGOGICA
            texto = "Gosto de apresentar trabalhos e me comunicar de forma clara para um grupo",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 4, lq = 4, lca = 4, adm = 2),
                7..8  to Pontuacao(lcc = 3, lq = 3, lca = 3, adm = 1),
                5..6  to Pontuacao(lcc = 1, lq = 2, lca = 2),
                3..4  to Pontuacao(lca = 1, lq = 1),
                1..2  to Pontuacao()
            )
        )
        val p20 = Pergunta(//PEDAGOGICA
            texto = "Gosto de adaptar a linguagem e o conteúdo para diferentes públicos (crianças, adolescentes, adultos).",
            regras = mapOf(
                9..10 to Pontuacao(lcc = 4, lca = 4, lq = 4, adm = 1),
                7..8  to Pontuacao(lcc = 3, lq = 3, lca = 3, adm = 2),
                5..6  to Pontuacao(lcc = 1, lq = 1, lca = 1, adm = 3),
                3..4  to Pontuacao(adm = 1),
                1..2  to Pontuacao()
            )
        )
        // A lista de perguntas agora é embaralhada ao ser criada.
        listaDePerguntas = listOf(p01, p02, p03, p04, p05, p06, p07, p08, p09, p10,
            p11, p12, p13, p14, p15, p16, p17, p18, p19, p20).shuffled()
    }
}

