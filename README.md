# Forum Api

POC de uma Api Rest de um sistema de Fórum de Perguntas e Respostas, com objetivo de estudo do padrão arquitetural '
Clean
Arquitecture' com 'Kotlin' :grin:

#### OBS.:

Foi utilizada uma simplificação do 'Clean Arquitecture' que se considerou mais adequada para implementação de
microsserviços.

## Definições do Projeto

### Convenções e Boas Práticas do Projeto

- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Arquitetura Limpa](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [SOLID](https://blog.cleancoder.com/uncle-bob/2020/10/18/Solid-Relevance.html)
- Testes de Unidade
- Testes de Integração

## Tecnologias do Projeto

- [Kotlin 1.9.0](https://kotlinlang.org/)
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot 2.7.13](https://spring.io/projects/spring-boot)
- [MySQL 8.0.29](https://www.mysql.com/)
- [Docker](https://www.docker.com/)
- [Gradle 7.4.1](https://gradle.org/)
- [Docker Compose 3](https://docs.docker.com/compose/compose-file/compose-file-v3/) (apenas para ambiente local)

## Como Executar o Projeto

### Ambiente Local

O ambiente local é o computador do desenvolvedor.

Para executar os passos abaixo é necessário que as seguintes portas esteja livres:

- `8080` para a aplicação
- `3306` para o MySQL dentro do container. Caso não queira usar o container, crie um banco e usuário conforme o
  arquivo `docker-compose.yaml` e pule o passo **3**.

**Sigas os passos:**

1) Baixe o projeto:

```shell
git clone https://github.com/brunoleonardo/poc-clean-architecture-api.git
```

2) Baixe as dependências do Gradle usando sua IDE (sugerimos a [IntelliJ](https://www.jetbrains.com/pt-br/idea/))


3) Crie o schema `forum` no seu banco de dados local.

```shell
CREATE SCHEMA forum;
```

4) Acesse a pasta do projeto e rode o comando do docker compose para inicialização dos contêineres utilizados pela
   aplicação. **Caso existam serviços rodando nas portas 3306 (MySQL), irá ocorrer um erro**:

```shell
docker-compose up
```

5) Usando a IDE, executar a classe principal `applicaton/src/main/kotlin/br/com/blas/forum/Startup.kt`.


6) Se tudo der certo, a aplicação irá subir em `localhost:8080` e você já será redirecionado para a documentação do
   Swagger.
