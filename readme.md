<H1>Trabalho de Desenvolvimento Mobile</H1>

<h3>Objetivo:</h3> 

**Desenvolver um jogo em Kotlin com um numero X de clicks necessários para vencer**

- [x] Imagens Representando os Estágios da jornada
- [x] Gerar um numero X de clicks necessários para vencer e mostrar o progresso conforme a imagem
- [x] Botão de desistencia
- [x] Mensagem de vitória

<strong>Explicação do código:</strong>

```

fun JourneyApp(onExit: () -> Unit) {}


```
Função Principal do projeto onde é realizado toda a estruturação do jogo
e também onde é realizado todas as chamadas para as demais funções.

Temos nela a definição do estágio do Jogo definido por:

```

 val progress = clicks.toDouble() / targetClicks

        stage = when {
            progress >= 1.0 -> COMPLETE 
            progress >= 0.66 -> FINAL 
            progress >= 0.33 -> MID 
            else -> INITIAL 
        }

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
                if (!showDialog) { 
                    showDialog = true 
                }
            }
            GIVE_UP -> {

                if (!showGiveUpDialog) { 
                    showGiveUpDialog = true 
                }

            }
        }


```

Onde **progress** é a quantidade de clicks do usuário, divido pela quantidade de clicks
necessários para vencer.

A partir desta informação obtemos o **stage**
que é de fato em qual parte do game o usuário se encontra, 
e tendo em mãos o **stage** definimos qual imagem será mostrada ao usuário(representando o estágio da jornada). 

Temos ainda na mesma função a seguinte validação:

```
if (stage != COMPLETE && stage != GIVE_UP) {
            Button(onClick = {

                clicks++ 

            }) {

                Text("valor: ${clicks}") 
            }

            Spacer(modifier = Modifier.width(8.dp)) 

            Button(onClick = {
                stage = GIVE_UP 
                showGiveUpDialog = true 
            }) {
                Text("Desistir") 
            }
        }

```

Onde ainda baseado no **stage** é definido se será exibido o botão de Clicks e o botão de desistencia.
Ainda no mesmo trecho é preenchido as váriaveis de controle que definem se vão ser exibidos 
as mensagens de desistencia ou finalização do jogo(vitória).
Que são chamados no seguinte bloco abaixo

```

  if (showDialog) {
            CongratulationDialog(
                onPlayAgain = {
                    
                    clicks = 0
                    targetClicks = Random.nextInt(1, 51)
                    showDialog = false
                    stage = INITIAL
                }
            )
        }


        if (showGiveUpDialog) {
            GiveUpDialog(
                onPlayAgain = {
                    
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

```
Abaixo temos o código responsavel por realizar o recomeço da jornada ou desistencia
definido por:

```

fun GiveUpDialog(onPlayAgain: () -> Unit, onExit: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Você desistiu!") }, 
        text = { Text(text = "Novo jogo?")
            Image(
                painter = painterResource(id = R.drawable.baumal),
                contentDescription = "Imagem de Desistência",
                modifier = Modifier.size(100.dp)
            )}, // Texto e imagem do diálogo
        confirmButton = {
            Button(onClick = onPlayAgain) {
                Text("Recomeçar") 
            }
        },
        dismissButton = {
            Button(onClick = onExit) {
                Text("Sair") 
            }
        }
    )
}


```
Temos dois botões principais: **onClick=onPlayAgain** e **onClick=onExit**
As duas invocam funções Lambda sem retorno e nelas na chamada é resetado o jogo
como segue este exemplo:

```
 GiveUpDialog(
                onPlayAgain = {
                    clicks = 0
                    targetClicks = Random.nextInt(1, 51)
                    stage = INITIAL
                    showGiveUpDialog = false
                },
                onExit = onExit
            )

```
**onPlayAgain** redefine os clicks para 0 e o estagio do jogo para inicial
resetando o game

A seguir temos a função que foi criada de forma generica
para mostrar as imagens durante a jornada do usuario:

```

fun ImageStage(imageResId: Int, description: String) {
    Image(
        painter = painterResource(id = imageResId), 
        contentDescription = description, 
        modifier = Modifier
            .size(200.dp) 
            .clickable { }
    )
}

```
**painter** define qual imagem sera exibida e **contentDescription**
a descrição da mesma visando a acessibilidade

Abaixo temos a mensagem de vitoria para o usuario, onde se apresenta
um botão para recomeçar o game **onClick=onPlayAgain**  e um
botão para sair do jogo **onClick = {activity?.finish()}**
seguindo a mesma lógica do **GiveUpDialog**

```

fun CongratulationDialog(onPlayAgain: () -> Unit) {

    val context = LocalContext.current
    val activity = context as? Activity

    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Parabéns!") }, 
        text = { Text("Você completou a jornada!") }, 
        confirmButton = {
            Button(onClick = onPlayAgain) {
                Text("Jogar Novamente") 
            }
        },
        dismissButton = {
            Button(onClick = {activity?.finish()}) {
                Text("Sair") 
            }
        }
    )
}


```
Por ultimo temos o Enum que define 
o estagio do jogador durante sua jornada

```

enum class Stage {

    INITIAL, MID, FINAL, COMPLETE, GIVE_UP

}


```

<h4>Equipe:</h4>

- João Pedro
- Samuel Franciso
- Gabriela Rodrigues
- Bruno Pantaleão
