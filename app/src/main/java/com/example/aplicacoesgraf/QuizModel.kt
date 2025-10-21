package com.example.aplicacoesgraf

// Este arquivo agora é a ÚNICA fonte para estas definições.

enum class Cursoo : java.io.Serializable {
    LCC, // Lic. em Ciências da Computação
    LCA, // Lic. em Ciências Agrárias
    LQI,  // Lic. em Química
    ADM  // Bacharel em Administração
}

data class Pontuacao(
    val lcc: Int = 0,
    val lca: Int = 0,
    val lq: Int = 0,
    val adm: Int = 0
)

data class Pergunta(
    val texto: String,
    val regras: Map<IntRange, Pontuacao>
)