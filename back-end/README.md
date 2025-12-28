# QBank API - Back-end

API RESTful para sistema bancário desenvolvida com Micronaut Framework, implementando operações bancárias completas com arquitetura em camadas e padrões de projeto.

## Tecnologias

- **Java 17**
- **Micronaut Framework 4.6.3**
- **Netty** - Servidor HTTP assíncrono
- **Jackson** - Serialização/Desserialização JSON
- **Gson** - Manipulação JSON
- **JUnit 5** - Testes unitários
- **Mockito 5.5.0** - Mock objects para testes
- **Logback** - Logging
- **Maven** - Gerenciamento de dependências

## Arquitetura

```
src/main/java/qbank/sistemabancario/
├── controllers/        # Camada de apresentação (endpoints REST)
├── dto/               # Services e objetos de transferência
├── context/           # Entidades de domínio (Conta, Pessoa)
└── strategy/          # Padrões de projeto (Strategy para transações)
```

## Funcionalidades

### Gestão de Contas

- Criação de contas (Corrente, Poupança, Salário)
- Autenticação por agência e número
- Atualização de dados cadastrais
- Consulta de informações
- Exclusão de contas

### Transações Financeiras

- Transferências (PIX e TED)
- Consulta de extrato
- Validação de saldo
- Histórico de movimentações

### Padrões Implementados

- **Strategy Pattern** - Diferentes tipos de transações
- **Service Layer** - Lógica de negócio isolada
- **DTO Pattern** - Transferência de dados entre camadas
- **Dependency Injection** - Injeção de dependências com Micronaut

## Execução

### Requisitos

- JDK 17 ou superior
- Maven 3.6+

### Iniciar servidor

```bash
./mvnw mn:run
```

A API estará disponível em `http://localhost:8080`

### Executar testes

```bash
./mvnw test
```

## API Endpoints

### Autenticação

#### Login

```http
POST /login
Content-Type: application/json

{
  "agencia": "746",
  "numero": "234758"
}
```

**Resposta:**

```json
{
  "tipoConta": "Corrente",
  "agencia": "746",
  "numero": "234758",
  "saldo": 7500.0,
  "pessoa": {
    "nome": "Eliseu",
    "cpf": "04486504197",
    "rg": "2334517",
    "email": "eueliseeu@gmail.com",
    "telefone": "66992654869"
  }
}
```

### Gestão de Contas

#### Criar Nova Conta

```http
POST /user/new/{tipoConta}
Content-Type: application/json

{
  "nome": "Maria do Carmo",
  "cpf": "12345678917",
  "rg": "12313",
  "email": "maria@gmail.com",
  "telefone": "123412314"
}
```

Tipos de conta: `corrente`, `poupanca`, `salario`

**Resposta:**

```json
{
  "tipoConta": "Corrente",
  "agencia": "739",
  "numero": "316439",
  "saldo": 0.0,
  "pessoa": {
    "nome": "Maria do Carmo",
    "cpf": "12345678917",
    "rg": "12313",
    "email": "maria@gmail.com",
    "telefone": "123412314"
  }
}
```

#### Editar Dados Cadastrais

```http
PUT /user/edit/{numeroConta}
Content-Type: application/json

{
  "nome": "Mateus Cavalo",
  "cpf": "3131314123",
  "rg": "12313",
  "email": "mateus@gmail.com",
  "telefone": "123412314"
}
```

#### Consultar Dados por Conta

```http
GET /user/search/{numeroConta}
```

**Resposta:**

```json
{
  "nome": "Eliseu",
  "cpf": "04486504197",
  "rg": "2334517",
  "email": "eueliseeu@gmail.com",
  "telefone": "66992654869"
}
```

#### Excluir Conta

```http
DELETE /user/delete/{numeroConta}
```

**Resposta:**

```
Conta excluída com sucesso
```

### Transações

#### Realizar Transferência

```http
POST /transacoes/deposito/{numeroConta}
Content-Type: application/json

{
  "contaOrigem": {
    "agencia": "746",
    "numero": "234758"
  },
  "contaDestino": {
    "agencia": "753",
    "numero": "929601"
  },
  "valor": 1000.0,
  "dataHora": "2023-12-01T10:00:00",
  "tipo": "TED"
}
```

Tipos de transação: `PIX`, `TED`

#### Consultar Extrato

```http
GET /transacoes/extrato/{numeroConta}
```

**Resposta:**

```json
[
  {
    "id": "1",
    "contaOrigem": {
      "agencia": "746",
      "numero": "234758"
    },
    "contaDestino": {
      "agencia": "753",
      "numero": "929601"
    },
    "valor": 1000.0,
    "dataHora": "2023-12-01T10:00:00",
    "tipo": "TED"
  }
]
```

## Estrutura de Dados

### Tipos de Conta

- **Conta Corrente** - Conta padrão para movimentações
- **Conta Poupança** - Conta com rendimento
- **Conta Salário** - Conta para recebimento de salário

### Entidades Principais

- **Pessoa** - Dados cadastrais do cliente
- **Conta** - Informações da conta bancária
- **Transacao** - Registro de movimentações financeiras

## Testes

O projeto inclui testes unitários para:

- Controllers (LoginController, ContaController)
- Services (ContaService, TransacaoService)
- Contextos (Conta, Pessoa)

Cobertura de testes inclui validação de regras de negócio, casos de sucesso e tratamento de exceções.

## Autores

- [Ronie Soares](https://github.com/Ronie-Soares)
- [Euelis Eeu](https://github.com/eueliseeu)
