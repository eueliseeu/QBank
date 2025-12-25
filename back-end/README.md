# Projeto Qbank - A3


## Descrição do Projeto
O **Qbank** é um sistema bancário desenvolvido para permitir que os clientes realizem diversas operações financeiras por meio de um aplicativo. As funcionalidades incluem o gerenciamento de contas, transações, empréstimos, cartões de crédito e pagamentos de serviços externos.

---

## Dependências

Para construir e executar o projeto, você precisará incluir as seguintes dependências no seu arquivo `pom.xml`:

### Micronaut 4.6.6
- **Micronaut HTTP Server**: Para criação de servidores HTTP.
- **Micronaut Serialization Jackson**: Para serialização e desserialização de objetos JSON.

### Mockito
- **Mockito**: Biblioteca de simulação para testes em Java, utilizada para criar objetos simulados em testes unitários.

### Dependências do `pom.xml`
```xml
<dependencies>
  <dependency>
    <groupId>io.micronaut</groupId>
    <artifactId>micronaut-http-server-netty</artifactId>
    <scope>compile</scope>
  </dependency>
  <dependency>
    <groupId>io.micronaut.serde</groupId>
    <artifactId>micronaut-serde-jackson</artifactId>
    <scope>compile</scope>
  </dependency>
  <dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <scope>runtime</scope>
  </dependency>
  <dependency>
    <groupId>io.micronaut</groupId>
    <artifactId>micronaut-http-client</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>io.micronaut.test</groupId>
    <artifactId>micronaut-test-junit5</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <scope>test</scope>
    <version>5.5.0</version>
  </dependency>
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.14.1</version>
  </dependency>
  <dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.9</version>
  </dependency>
</dependencies>
```

---

## Funcionalidades do Sistema

### 1. Módulo de Contas
- **Criação de Contas**: Permite que os usuários criem novas contas.
- **Visualização de Contas**: Os usuários podem visualizar suas contas ativas.
- **Atualização de Contas**: Possibilidade de modificar os dados da conta.
- **Exclusão de Contas**: Opção para encerrar contas existentes.

### 2. Módulo de Transações
- **Transferências**: Realização de transferências para:
    - Contas próprias
    - Terceiros
    - Outros bancos



## Estrutura Modular

O sistema é organizado em módulos distintos, cada um abordando uma área específica de operações bancárias, o que facilita a navegação e a experiência do usuário. A modularidade garante que cada funcionalidade possa ser aprimorada ou expandida sem afetar as demais.


## ROTA PARA FAZER UMA TRANSFERENCIA:
*METODO POST = http://localhost:8080/transacoes/deposito/{numeroConta}*
### PASSAR O NUMERO DA CONTA ORIGEM NO PARAMETRO DA REQUISIÇÃO
    Exemplo: http://localhost:8080/transacoes/deposito/{234758}
### PASSSAR AS CONTA E AGENCIA NO BODY DA REQUISIÇÃO E VALOR, DATA E TIPO ('PIX' ou 'TED')
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
### RETORNO
    Ainda deve ser definido

## ROTA PARA BUSCAR EXTRATO:
*METODO GET = http://localhost:8080/transacoes/extrato//{numeroConta}*
### PASSAR O NUMERO DA CONTA NO PARAMETRO DA REQUISIÇÃO - MOVIMENTAÇÃO DE ENTRADA E SAÍDA
    Exemplo: http://localhost:8080/transacoes/extrato/929601
### RETORNO
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
	},
	{
		"id": "2",
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
	},
	{
		"id": "3",
		"contaOrigem": {
			"agencia": "753",
			"numero": "929601"
		},
		"contaDestino": {
			"agencia": "708",
			"numero": "37846"
		},
		"valor": 1000.0,
		"dataHora": "2023-12-01T10:00:00",
		"tipo": "TED"
	}
]

<hr>

# ROTAS PARA CRIAÇÃO E GERENCIAMENTO DE CONTA:
## ROTA PARA LOGIN E RECEBE OS DADOS DA CONTA: 
*METODO POST = http://localhost:8080/login}*
### PASSSAR OS CONTA E AGENCIA NO BODY DA REQUISIÇÃO
    {
        "agencia": "746",
        "numero": "234758"
    }
#### RETORNO
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
      },
      "tipoConta": "Corrente"
    }

## ROTA PARA CRIAR NOVA CONTA:
*METODO POST = http://localhost:8080/user/new/{tipoConta}*
### PASSSAR OS DADOS PESSOAIS NO BODY DA REQUISIÇÃO
    {
    "nome": "Maria do Carmo",
    "cpf": "12345678917",
    "rg": "12313",
    "email": "maria@gmail.com",
    "telefone": "123412314"
    
    }
### PASSAR O TIPO DE CONTA COMO PARAMETRO DA REQUISIÇÃO
    corrente, polpança ou salario
#### RETORNO
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

## ROTA PARA EDITAR CONTA - APENAS DADOS PESSOAIS:
*METODO PUT = http://localhost:8080/user/dit/{numeroConta}*
### PASSSAR OS DADOS PESSOAIS NO BODY DA REQUISIÇÃO
    {
    "nome": "Mateus cavalo",
    "cpf": "3131314123",
    "rg": "12313",
    "email": "mateus@gmail.com",
    "telefone": "123412314"
    
    }

#### RETORNO
    {
    "tipoConta": "Salário",
    "agencia": "826",
    "numero": "536508",
    "saldo": 3000.0,
    "pessoa": {
    "nome": "Mateus cavalo",
    "cpf": "3131314123",
    "rg": "12313",
    "email": "mateus@gmail.com",
    "telefone": "123412314"
    }
    }

## ROTA PARA BUSCAR DADOS PESSOAIS POR NUMERO DE CONTA:
*METODO GET = http://localhost:8080/user/search/{numeroConta}*
### PASSAR O NUMERO DA CONTA COMO PARAMETRO DA REQUISIÇÃO
    numeroConta = 123456
    http://localhost:8080/user/search/123456

#### RETORNO
    {
    "nome": "Eliseu",
    "cpf": "04486504197",
    "rg": "2334517",
    "email": "eueliseeu@gmail.com",
    "telefone": "66992654869"
    }

## ROTA PARA EXCLUIR CONTA:
*METODO DELETE = http://localhost:8080/user/delete/{numeroConta}*
### PASSAR NUMERO DA CONTA COMO PARAMETRO DA REQUISIÇÃO
    numeroConta = 171484
    http://localhost:8080/user/delete/171484

#### RETORNO
    Conta excluída com sucesso
