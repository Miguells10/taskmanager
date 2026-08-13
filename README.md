# Task Manager API

API REST para criação, listagem, consulta e exclusão de tarefas. O projeto foi desenvolvido com Java e Spring Boot como parte de um bootcamp da DIO.

## Tecnologias

- Java 24
- Spring Boot 4.1
- Gradle
- JUnit 5 e Mockito
- OpenAPI/Swagger

## Como executar

É necessário ter o JDK 24 instalado.

```powershell
.\gradlew.bat bootRun
```

A aplicação inicia em `http://localhost:8080`.

## Documentação da API

Com a aplicação em execução, acesse:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Documento OpenAPI: `http://localhost:8080/v3/api-docs`

## Endpoints

| Método | Rota | Descrição | Respostas |
| --- | --- | --- | --- |
| `POST` | `/tasks` | Cria uma tarefa | `201`, `400` |
| `GET` | `/tasks` | Lista todas as tarefas | `200` |
| `GET` | `/tasks/{id}` | Consulta uma tarefa pelo identificador | `200`, `404` |
| `DELETE` | `/tasks/{id}` | Exclui uma tarefa pelo identificador | `204`, `404` |

### Criar uma tarefa

```json
{
  "title": "Estudar Java",
  "description": "Concluir a aula de API REST"
}
```

`title` é obrigatório e não pode ser vazio. `description` é opcional.

Exemplo de resposta:

```json
{
  "id": "f5bb8ee7-5277-4596-a87c-4a81c61c808c",
  "title": "Estudar Java",
  "description": "Concluir a aula de API REST",
  "status": "Pending"
}
```

## Testes

```powershell
.\gradlew.bat test
```

## Persistência

As tarefas são armazenadas em memória. Portanto, os dados são removidos quando a aplicação é encerrada.
