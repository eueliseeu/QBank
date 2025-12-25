package qbank.sistemabancario.dto;

import jakarta.inject.Singleton;
import qbank.sistemabancario.context.conta.Conta;
import qbank.sistemabancario.dto.Requests.MensageException;
import qbank.sistemabancario.dto.database.Database;
import qbank.sistemabancario.strategy.transacao.Transacao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Singleton
public class TransacaoService {
    static Database db;
    static List<Transacao> transacoes;
    static ContaService contaService;


    public TransacaoService() {
        db = Database.getInstance();
        transacoes = db.getTransacoes();
        contaService = new ContaService();
    }

    public Transacao buscarTransacoesPorNumero(String numeroTransacao) {
        try {
            for (Transacao transacao : transacoes) {
                if (transacao.getId().equals(numeroTransacao)) {
                    return transacao;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Transacao> ExtratoDeTransacoes(String numeroConta) {
        List<Transacao> extrato = new ArrayList<>();
        try {
            for (Transacao transacao : transacoes) {
                if (transacao.getContaOrigem().getNumero().equals(numeroConta) || transacao.getContaDestino().getNumero().equals(numeroConta)) {
                    extrato.add(transacao);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return extrato;
    }

    public String realizarTransaco(Transacao transacao) {
        //verifica condicoes de transacao (verificar se as contas existem)
        //valida todas as condicoes
        String validacao = verificarContasDasTransacoes(transacao);


        if (validacao.equals("inconclusiva")) {
            throw new MensageException("Dados Incorretos");
        } else if (validacao.equals("interna") || validacao.equals("externa")) {
            SalvarTransacaoBanco(transacao);
        }

        return validacao;
    }

    private void SalvarTransacaoBanco(Transacao transacao) {
        db.addContador();
        transacao.setId(String.valueOf(db.getContadorTransacoes()));
        transacoes.add(transacao);
        db.saveData();
    }

    private String verificarContasDasTransacoes(Transacao transacao) {
        String[] contaOrigem = new String[2];
        String[] contaDestino = new String[2];
        String movimentacaoInterna;

        contaOrigem[0] = transacao.getContaOrigem().getAgencia();
        contaOrigem[1] = transacao.getContaOrigem().getNumero();
        contaDestino[0] = transacao.getContaDestino().getAgencia();
        contaDestino[1] = transacao.getContaDestino().getNumero();


        if (verificarDadosRecebidos(contaOrigem[0], contaOrigem[1]) && verificarDadosRecebidos(contaDestino[0], contaDestino[1])) {
            movimentacaoInterna = "interna";
            realizarTransacao(contaDestino[1], transacao.getValor(), "entrada");
            realizarTransacao(contaOrigem[1], transacao.getValor(), "saida");

        } else if (!verificarDadosRecebidos(contaOrigem[0], contaOrigem[1]) && verificarDadosRecebidos(contaDestino[0], contaDestino[1])) {
            movimentacaoInterna = "externa";
            realizarTransacao(contaDestino[1], transacao.getValor(), "entrada");
        } else {
            movimentacaoInterna = "inconclusiva";
        }

        return movimentacaoInterna;
    }

    private boolean verificarDadosRecebidos(String agencia, String numeroConta) {
        try {
            Conta contaVerificada = contaService.buscarContaPorNumero(numeroConta);
            if (contaVerificada == null) {
                return false;
            } else {
                return Objects.equals(contaVerificada.getAgencia(), agencia);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void realizarTransacao(String conta, BigDecimal valor, String tipo) {
        Conta contaVerificada = contaService.buscarContaPorNumero(conta);

        if (tipo.equals("entrada")) {
            contaVerificada.setSaldo(contaVerificada.getSaldo().add(valor));
        } else if (tipo.equals("saida")) {
            contaVerificada.setSaldo(contaVerificada.getSaldo().subtract(valor));
        }

        try {
            db.updateConta(contaVerificada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificaTransacoes(String numeroConta) {
        List<Transacao> extrato = ExtratoDeTransacoes(numeroConta);

        if (extrato.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


}
