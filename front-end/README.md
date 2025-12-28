# QBank Client - Front-end

Interface desktop em Java Swing para sistema bancário, permitindo gestão completa de contas e transações através de comunicação com API REST.

## Tecnologias

- **Java 17** (com recursos preview habilitados)
- **Java Swing** - Framework GUI para interface desktop
- **HttpClient** - Cliente HTTP nativo do Java
- **Gson 2.8.9** - Serialização/Desserialização JSON
- **JSON.org** - Manipulação de dados JSON
- **Maven** - Gerenciamento de dependências

## Arquitetura

```
src/main/java/com/atividade/appbank/
├── AccountUser/        # Interfaces gráficas do usuário
│   ├── InterfaceUserLog.java      # Tela principal pós-login
│   ├── CreateAccount.java         # Criação de conta
│   ├── InfoUser.java             # Informações do usuário
│   ├── TransferUser.java         # Realização de transferências
│   └── NumericLimitFilter.java   # Validação de entrada
└── API/               # Comunicação com back-end
    └── API.java       # Cliente HTTP
```

## Funcionalidades

### Interface de Usuário

- Login com agência e número da conta
- Visualização de saldo e dados cadastrais
- Criação de novas contas
- Edição de informações pessoais
- Consulta de extrato
- Realização de transferências (PIX e TED)
- Atualização automática de saldo
- Validação de campos numéricos

### Integração com API

- Comunicação HTTP com back-end QBank
- Serialização/Desserialização de objetos JSON
- Tratamento de respostas e erros
- Sincronização de dados em tempo real

## Requisitos

- **JDK 17** ou superior
- **Maven 3.6+**
- **QBank API** executando em `http://localhost:8080`

## Execução

### Iniciar aplicação

```bash
mvn clean install
mvn exec:java
```

Ou usando o Maven wrapper:

```bash
mvn compile exec:java -Dexec.mainClass="com.atividade.appbank.AppBank"
```

### Configuração

Certifique-se de que a API do QBank está executando antes de iniciar o cliente:

```bash
cd ../back-end
./mvnw mn:run
```

## Fluxo de Uso

1. **Login**

   - Insira agência e número da conta
   - Sistema autentica via API REST

2. **Tela Principal**

   - Visualize saldo atual
   - Acesse funcionalidades através do menu

3. **Operações Disponíveis**

   - Consultar informações da conta
   - Realizar transferências
   - Visualizar extrato
   - Editar dados cadastrais

4. **Transferências**
   - Selecione tipo (PIX ou TED)
   - Informe dados da conta destino
   - Digite o valor
   - Confirme operação

## Estrutura de Telas

### InterfaceUserLog (Tela Principal)

- Exibição de saldo
- Menu de navegação
- Atualização automática de dados
- Acesso às demais funcionalidades

### CreateAccount (Criação de Conta)

- Formulário de dados pessoais
- Seleção de tipo de conta
- Validação de CPF e campos
- Integração com API para cadastro

### InfoUser (Informações do Usuário)

- Visualização de dados cadastrais
- Edição de informações
- Atualização via API

### TransferUser (Transferências)

- Formulário de transferência
- Seleção de tipo de transação
- Validação de valores
- Confirmação e execução

## Padrões e Boas Práticas

- **MVC Adaptado** - Separação entre UI e lógica de negócio
- **Componentização** - Telas modulares e reutilizáveis
- **Validação de Entrada** - Filtros customizados para campos
- **Tratamento de Exceções** - Gerenciamento robusto de erros
- **Clean Code** - Código organizado e legível

## Dependências (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>org.jsonx</groupId>
        <artifactId>json</artifactId>
        <version>0.2.2</version>
    </dependency>
    <dependency>
        <groupId>org.codeartisans</groupId>
        <artifactId>org.json</artifactId>
        <version>20161124</version>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.9</version>
    </dependency>
</dependencies>
```

## Recursos Preview Java 17

O projeto utiliza recursos preview do Java 17, habilitados através do argumento:

```xml
<compilerArgs>
    --enable-preview
</compilerArgs>
```

## Autores

- [Ronie Soares](https://github.com/Ronie-Soares)
- [Euelis Eeu](https://github.com/eueliseeu)
