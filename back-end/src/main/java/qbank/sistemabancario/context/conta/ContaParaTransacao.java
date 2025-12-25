package qbank.sistemabancario.context.conta;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class ContaParaTransacao {
    private String agencia;
    private String numero;

    public ContaParaTransacao() {
    }

    public ContaParaTransacao(String agencia, String numero) {
        this.agencia = agencia;
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }
}
