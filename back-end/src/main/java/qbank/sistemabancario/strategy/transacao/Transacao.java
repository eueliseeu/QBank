package qbank.sistemabancario.strategy.transacao;

import io.micronaut.serde.annotation.Serdeable;
import qbank.sistemabancario.context.conta.Conta;
import qbank.sistemabancario.context.conta.ContaParaTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
public class Transacao {
    private String id;
    private ContaParaTransacao contaOrigem;
    private ContaParaTransacao contaDestino;
    private BigDecimal valor;
    private String dataHora;
    private TipoTransacao tipo;

    public enum TipoTransacao {
        PIX, TED;
    }

    public Transacao() {}

    public Transacao(ContaParaTransacao contaOrigem, ContaParaTransacao contaDestino, BigDecimal valor, TipoTransacao tipo) {
        this.id = UUID.randomUUID().toString();
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.dataHora = String.valueOf(LocalDateTime.now());
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContaParaTransacao getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(ContaParaTransacao contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public ContaParaTransacao getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(ContaParaTransacao contaDestino) {
        this.contaDestino = contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id='" + id + '\'' +
                ", contaOrigem=" + contaOrigem.getNumero() +
                ", contaDestino=" + contaDestino.getNumero() +
                ", valor=" + valor +
                ", dataHora=" + dataHora +
                ", tipo=" + tipo +
                '}';
    }
}
