# EcoBot

**EcoBot** é um bot simples para Discord com a funcionalidade de tocar músicas utilizando plataforma SoundCloud.

A ideia surgiu a partir de uma conversa com amigos, onde um deles precisava de um bot de música que também pudesse executar algumas funções administrativas.

# Ferramenta Utilizadas 

- Java Development Kit (JDK)
- Maven
- JDA
- LavaPlayer
- Git

# Pré-requisitos

- JDK 8 ou superior
- Maven
- Git

# Tutorial de como o bot funciona

- 1º  Clonagem do repósitorio 
- 2º  Criação do Bot no Discord Developer
- 3º  Como iniciar o bot

# Como Rodar o Projeto

~~~~ 
   git clone https://github.com/Luis5Felipe/discord-music-bot.git
~~~~

# Criação do Bot no Discord Developer

- 1º Acesse o Discord [Developer Portal](https://discord.com/developers/applications)
- 2º Clique em "New Application" e escolha um nome
- 3º Vá até a aba **"Bot"** e clique em **"Reset Token"** para gerar o token
- 4º Crie um arquivo chamado .env na raiz do projeto e adicione seu token no seguinte formato:

````
    BOT_TOKEN=seu_token_aqui
````
# Iniciar o Bot

### Execute o seguinte comando no terminal:
````
mvn install
````
### E depois:
```
java -jar .\target\EcoBot-1.0-SNAPSHOT.jar
```
# Funcionalidades Atuais

- **/play** <link>  Conecta o bot ao canal de voz e adiciona a música na playlist
- **/stop** Desconecta o bot do canal
- **/ping** Verifica o tempo de resposta do bot

# Funcionalidades Planejadas 

- **/help** – Mostra todos os comandos disponíveis

- **/list** – Exibe a lista de músicas na fila

- **/skip** – Pula a música atual

- **/pause** – Pausa a música

- **/clear** – Limpa a fila de músicas

- Deploy do bot em uma plataforma online (ex: Render, Railway, Replit etc.)