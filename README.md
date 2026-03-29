# 🛡️ FraudChecker API: Engine de Análise de Risco

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Tests](https://img.shields.io/badge/Tests-13%20Passed-green?style=for-the-badge&logo=junit5)](https://junit.org/junit5/)

Este foi o MVP (Produto Mínimo Viável) onde foquei em Strategy Pattern e regras de negócio.
Esta é uma API robusta de detecção de fraudes que utiliza **análise heurística assíncrona** para proteger transações financeiras. O sistema avalia o comportamento do usuário em tempo real, atribuindo scores de risco baseados em padrões históricos e regras de negócio dinâmicas.

---

## 🧠 Arquitetura e Design Patterns

O projeto foi desenhado com foco em **escalabilidade** e **manutenibilidade**, utilizando conceitos avançados de engenharia de software:

* **Strategy Pattern**: As regras de fraude (`HighValue`, `NightTime`, `Frequency`) são desacopladas, permitindo adicionar novas regras sem alterar o código core.
* **Event-Driven Architecture (EDA)**: A análise de risco não trava a requisição do usuário. Utilizamos o `ApplicationEventPublisher` para processar o risco em background.
* **DIP (Dependency Inversion)**: O serviço depende de abstrações (`RiskRuleChecker`), facilitando a testabilidade com Mocks e a expansão do motor de regras.

---

## 📊 O Motor de Fraude (Fraud Engine)

Cada transação passa por um pipeline de análise que soma pontos de risco. Se o score ultrapassar o limite aceitável ou se regras críticas forem violadas, a transação é marcada como **REJECTED**.

| Regra | Lógica de Detecção | Peso / Impacto |
| :--- | :--- | :--- |
| **High Value** | Cálculo progressivo de risco baseado no montante (valor/100). | Médio/Alto |
| **Night Time** | Identifica operações entre 00h e 05h (horário de alta vulnerabilidade). | Fixo (+20 pts) |
| **Frequency** | Detecta "Spam" de transações (>5 em 1h) para a mesma conta. | Dinâmico (+20/tx extra) |
| **Hard Limit** | Bloqueio imediato para transações acima de R$ 10.000,00. | **Rejeição Direta** |

---

## 🚀 Endpoints Principais

A API está totalmente documentada via **Swagger (OpenAPI)**.

* `POST /accounts`: Registra um novo cliente.
* `GET /accounts/{id}/risk-profile`: Retorna o resumo de risco consolidado (Ticket médio, score acumulado e status).
* `POST /transactions`: Recebe a transação e inicia a análise assíncrona.
* `GET /transactions/account/{id}`: Histórico completo de transações e seus status de aprovação.

---

## 🧪 Qualidade de Código e Testes

O projeto segue a pirâmide de testes, com **100% de cobertura nas regras de negócio críticas**:
* **Testes Unitários**: Validação da precisão matemática usando `BigDecimal`, garantindo corretude em operações financeiras.
* **Testes de Serviço**: Uso de `@Mock` e `@Spy` (Mockito) para simular persistência e validar o disparo de eventos assíncronos.
* **Resultados**: 13 testes executados e aprovados com sucesso.

---

## 🛠️ Execução e Setup

1.  **Clonar o repositório:** ```bash
    git clone [https://github.com/Fabiana-mds/fraud-detection-api](https://github.com/Fabiana-mds/fraud-detection-api)
    ```
2.  **Banco de Dados:** O projeto utiliza **Flyway** para migrações automáticas. Certifique-se de ter o PostgreSQL rodando ou utilize o `docker-compose.yml` incluso.
3.  **Rodar a aplicação:** ```bash
    ./mvnw spring-boot:run
    ```
4.  **Documentação Interativa:** Acesse `http://localhost:8080/swagger-ui.html` para testar os endpoints.

---

## 👩‍💻 Autor
**Fabiana Morais** *Backend Developer Java* "Focada em construir sistemas seguros, resilientes e orientados a boas práticas de arquitetura."












































