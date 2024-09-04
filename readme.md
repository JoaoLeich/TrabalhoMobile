#Trabalho de Desenvolvimento Mobile

**Objetivo:** **Desenvolver um jogo em Kotlin com um numero X de clicks necessários para vencer**

- [x] Imagens Representando os Estágios da jornada
- [x] Gerar um numero X de clicks necessários para vencer e mostrar o progresso conforme a imagem
- [x] Botão de desistencia
- [x] Mensagem de vitória

#Explicação do código: 

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
que é de fato em qual parte do game o usuário se encontra 
E Tendo em mãos o **stage** definimos qual imagem será mostrada ao usuário(representando o estágio da jornada). 

```

fun GiveUpDialog(onPlayAgain: () -> Unit, onExit: () -> Unit){}


```

```

fun ImageStage(imageResId: Int, description: String){}


```


```

fun CongratulationDialog(onPlayAgain: () -> Unit) {}


```

```

enum class Stage {

    INITIAL, MID, FINAL, COMPLETE, GIVE_UP

}


```

###Equipe:

- João Pedro
- Samuel Franciso
- Gabriela Rodrigues
- Bruno Pantaleão