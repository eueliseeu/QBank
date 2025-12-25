package qbank.sistemabancario.context.conta;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.micronaut.serde.annotation.Serdeable;
import qbank.sistemabancario.context.pessoa.Pessoa;

import java.math.BigDecimal;

@Serdeable
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoConta")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContaCorrente.class, name = "Corrente"),
        @JsonSubTypes.Type(value = ContaPoupanca.class, name = "Poupança"),
        @JsonSubTypes.Type(value = ContaSalario.class, name = "Salário")
})
public abstract class Conta {
    private String agencia;
    private String numero;
    private BigDecimal saldo;
    private Pessoa pessoa;
    //private String tipoConta;

    // Construtor padrão
    public Conta() {
    }

    public Conta(String agencia, String numero, BigDecimal saldo, Pessoa pessoa, String tipoConta) {
        this.agencia = agencia;
        this.numero = numero;
        this.saldo = saldo;
        this.pessoa = pessoa;
        //this.tipoConta = tipoConta;
    }

    public String getAgencia() {
        return agencia;
    }
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

//    public String getTipoConta() {
//        return tipoConta;
//    }
//    public void setTipoConta(String tipoConta) {
//        this.tipoConta = tipoConta;
//    }
}

