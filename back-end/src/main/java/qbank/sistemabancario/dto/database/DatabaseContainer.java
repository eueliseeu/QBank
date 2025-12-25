package qbank.sistemabancario.dto.database;

import io.micronaut.serde.annotation.Serdeable;
import qbank.sistemabancario.context.conta.Conta;
import qbank.sistemabancario.context.conta.ContaCorrente;
import qbank.sistemabancario.strategy.transacao.Transacao;

import java.util.List;

@Serdeable
public class DatabaseContainer {
    //aqui Importar as Classes que são gravadas
    private List<Conta> contas;
    private List<Transacao> transacoes;
    private Integer contadorTransacoes;

    public DatabaseContainer() {}

    public DatabaseContainer(List<Conta> contas, List<Transacao> transacoes, Integer contadorTransacoes) {
        this.contas = contas;
        this.transacoes = transacoes;
        this.contadorTransacoes = contadorTransacoes;

    }

    public List<Conta> getContas() { return contas; }
    public List<Transacao> getTransacoes() { return transacoes; }
    public Integer getContadorTransacoes(){return contadorTransacoes;}
}
