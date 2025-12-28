# QBank

Sistema bancário completo desenvolvido como projeto acadêmico, implementando operações financeiras fundamentais através de uma arquitetura cliente-servidor robusta.

## Visão Geral

QBank é uma aplicação bancária que permite gerenciamento de contas, transações financeiras e autenticação de usuários. O projeto demonstra a aplicação de conceitos de engenharia de software, padrões de projeto e desenvolvimento full-stack.

## Arquitetura

- **Back-end**: API RESTful construída com Micronaut Framework
- **Front-end**: Aplicação desktop em Java
- **Persistência**: Sistema de armazenamento baseado em JSON

## Tecnologias

### Back-end

- Java 17
- Micronaut Framework 4.6.3
- Netty (servidor HTTP)
- Jackson (serialização JSON)
- Gson
- JUnit 5 e Mockito (testes)
- Maven

### Front-end

- Java 17
- Gson (integração com API)
- Maven

## Funcionalidades

- Autenticação e autorização de usuários
- Gerenciamento de contas bancárias (Corrente, Poupança, Salário)
- Operações de transações financeiras
- Padrão Strategy para diferentes tipos de transações
- API RESTful para comunicação cliente-servidor

## Estrutura do Projeto

```
QBank/
├── back-end/          # API REST com Micronaut
│   ├── controllers/   # Endpoints da API
│   ├── dto/          # Objetos de transferência e serviços
│   ├── context/      # Entidades de domínio
│   └── strategy/     # Implementação de padrões de projeto
└── front-end/        # Interface desktop
```

## Execução

### Back-end

```bash
cd back-end
./mvnw mn:run
```

### Front-end

```bash
cd front-end
mvn compile exec:java
```

## Habilidades Desenvolvidas

- Desenvolvimento de APIs RESTful
- Arquitetura de software multicamadas
- Padrões de projeto (Strategy, DTO, Service Layer)
- Testes unitários e integração
- Gerenciamento de dependências com Maven
- Princípios SOLID
- Versionamento com Git

## Autores

Projeto desenvolvido por:

- [Ronie Soares](https://github.com/Ronie-Soares)
- [Eliseu Pereira](https://github.com/eueliseeu)

## Licença

Projeto acadêmico desenvolvido para fins educacionais.
