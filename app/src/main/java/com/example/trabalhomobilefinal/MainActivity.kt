package com.example.trabalhomobilefinal

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.trabalhomobilefinal.Stage.COMPLETE
import com.example.trabalhomobilefinal.Stage.FINAL
import com.example.trabalhomobilefinal.Stage.GIVE_UP
import com.example.trabalhomobilefinal.Stage.INITIAL
import com.example.trabalhomobilefinal.Stage.MID
import com.example.trabalhomobilefinal.ui.theme.TrabalhoMobileFinalTheme
import kotlin.random.Random
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

// MainActivity é a atividade principal do aplicativo
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Chama o método da superclasse
        enableEdgeToEdge() // Habilita o modo de tela cheia
        setContent {
            TrabalhoMobileFinalTheme {
                // Define o conteúdo da tela principal usando o tema do aplicativo
                JourneyApp(onExit = { finish() }) // Inicia o aplicativo com a função de saída
            }
        }
    }
}

// ------------------

// Composable function que define o layout e a lógica do aplicativo
@Composable
fun JourneyApp(onExit: () -> Unit) {
    val bundle = Bundle()
    // Declaração de variáveis de estado
    var clicks by rememberSaveable  { mutableStateOf(0) } // Conta o número de cliques
    var targetClicks by remember { mutableStateOf(Random.nextInt(1, 51)) } // Define um número aleatório de cliques alvo
    var stage by remember { mutableStateOf(Stage.INITIAL) } // Controla o estágio atual do jogo
    var showDialog by remember { mutableStateOf(false) } // Controla a exibição do diálogo de vitória
    var showGiveUpDialog by remember { mutableStateOf(false) } // Controla a exibição do diálogo de desistência

    // Estrutura da UI usando uma coluna centralizada
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa todo o espaço disponível
        verticalArrangement = Arrangement.Center, // Alinha verticalmente ao centro
        horizontalAlignment = Alignment.CenterHorizontally // Alinha horizontalmente ao centro
    ) {
        // Calcula o progresso baseado nos cliques do usuário
        val progress = clicks.toDouble() / targetClicks
        stage = when {
            progress >= 1.0 -> COMPLETE // Se o progresso é 100% ou mais, estágio completo
            progress >= 0.66 -> FINAL // Se o progresso está entre 66% e 99%, estágio final
            progress >= 0.33 -> MID // Se o progresso está entre 33% e 65%, estágio intermediário
            else -> INITIAL // Se o progresso é menor que 33%, estágio inicial
        }

        // Exibe a imagem correspondente ao estágio atual
        when (stage) {
            INITIAL -> {
                ImageStage(R.drawable.baufechado, "Começo da Jornada")
            }
            MID -> {
                ImageStage(R.drawable.bauvazio, "Progresso Intermediário")
            }
            FINAL -> {
                ImageStage(R.drawable.baucheio, "Próximo da Conquista")
            }
            COMPLETE -> {
                ImageStage(R.drawable.baumuitocheio, "Conquista Completa!")
                if (!showDialog) { // Se o estágio é completo e o diálogo ainda não foi mostrado
                    showDialog = true // Exibe o diálogo de vitória
                }
            }
            GIVE_UP -> {
                ImageStage(R.drawable.baumal, "Desistência")
                if (!showGiveUpDialog) { // Se o estágio é desistência e o diálogo ainda não foi mostrado
                    showGiveUpDialog = true // Exibe o diálogo de desistência
                }

                Text(text = "Textosiohjioasdhfbsdjibfjoisdbfrsdjiofgbojsdbuof")

            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço entre os elementos

        // Exibe os botões "Avançar" e "Desistir" se o estágio não for de conclusão ou desistência
        if (stage != COMPLETE && stage != GIVE_UP) {
            Button(onClick = {

                clicks++ // Incrementa o número de cliques quando o botão "Avançar" é pressionado


            }) {


                Text("valor: ${clicks}") // Texto exibido no botão
            }

            Spacer(modifier = Modifier.width(8.dp)) // Espaço horizontal entre os botões

            Button(onClick = {
                stage = GIVE_UP // Define o estágio como desistência
                showGiveUpDialog = true // Exibe o diálogo de desistência
            }) {
                Text("Desistir") // Texto exibido no botão
            }
        }

        // Exibe o diálogo de parabéns se o estágio for completo
        if (showDialog) {
            CongratulationDialog(
                onPlayAgain = {
                    // Reseta o jogo ao clicar em "Jogar Novamente"
                    clicks = 0
                    targetClicks = Random.nextInt(1, 51)
                    showDialog = false
                    stage = INITIAL
                }
            )
        }

        // Exibe o diálogo de desistência se o estágio for desistência
        if (showGiveUpDialog) {
            GiveUpDialog(
                onPlayAgain = {
                    // Reseta o jogo ao clicar em "Sim" no diálogo de desistência
                    clicks = 0
                    targetClicks = Random.nextInt(1, 51)
                    stage = INITIAL
                    showGiveUpDialog = false
                },
                onExit = onExit
            )
        }
    }
}

fun onSaveInstanceState(extra: Bundle) {
    extra.putInt("count",extra.getInt("count"))
}


fun onCreate(extra: Bundle?) {
    if (extra != null) {
        val value = extra.getInt("count")
    }
}

// Composable function que exibe o diálogo de desistência
@Composable
fun GiveUpDialog(onPlayAgain: () -> Unit, onExit: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Você desistiu!") }, // Título do diálogo
        text = { Text(text = "Novo jogo?")
            Image(
                painter = painterResource(id = R.drawable.baumal),
                contentDescription = "Imagem de Desistência",
                modifier = Modifier.size(100.dp)
            )}, // Texto e imagem do diálogo
        confirmButton = {
            Button(onClick = onPlayAgain) {
                Text("Recomeçar") // Botão para reiniciar o jogo
            }
        },
        dismissButton = {
            Button(onClick = onExit) {
                Text("Sair") // Botão para sair do aplicativo
            }
        }
    )
}

// Composable function que exibe a imagem de acordo com o estágio atual
@Composable
fun ImageStage(imageResId: Int, description: String) {
    Image(
        painter = painterResource(id = imageResId), // Recurso de imagem a ser exibido
        contentDescription = description, // Descrição da imagem para acessibilidade
        modifier = Modifier
            .size(200.dp) // Define o tamanho da imagem
            .clickable { } // A imagem pode ser clicada, mas não faz nada aqui
    )
}

// Composable function que exibe o diálogo de parabéns
@Composable
fun CongratulationDialog(onPlayAgain: () -> Unit) {

    val context = LocalContext.current
    val activity = context as? Activity

    AlertDialog(
        onDismissRequest = {}, // Não permite que o diálogo seja descartado ao clicar fora
        title = { Text(text = "Parabéns!") }, // Título do diálogo
        text = { Text("Você completou a jornada!") }, // Texto principal do diálogo
        confirmButton = {
            Button(onClick = onPlayAgain) {
                Text("Jogar Novamente") // Botão para jogar novamente
            }
        },
        dismissButton = {
            Button(onClick = {activity?.finish()}) {
                Text("Sair") // Botão para sair ou encerrar o aplicativo
            }
        }
    )
}

// Enumeração para representar os diferentes estágios do jogo
enum class Stage {
    INITIAL, MID, FINAL, COMPLETE, GIVE_UP
}
