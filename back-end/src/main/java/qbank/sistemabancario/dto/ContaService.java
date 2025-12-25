package qbank.sistemabancario.dto;

import jakarta.inject.Singleton;
import qbank.sistemabancario.context.conta.Conta;
import qbank.sistemabancario.context.conta.ContaCorrente;
import qbank.sistemabancario.context.conta.ContaPoupanca;
import qbank.sistemabancario.context.conta.ContaSalario;
import qbank.sistemabancario.context.pessoa.Pessoa;
import qbank.sistemabancario.dto.Requests.MensageException;
import qbank.sistemabancario.dto.database.Database;
import qbank.sistemabancario.strategy.transacao.Transacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Singleton
public class ContaService {
    static Database db;
    static List<Conta> contas;

    public ContaService() {
        db = Database.getInstance();
        contas = db.getContas();
    }

    public Conta buscarContaPorNumero(String numeroConta) {
        try {
            for (Conta conta : contas) {
                if (conta.getNumero().equals(numeroConta)) {
                    return conta;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Conta buscarContaPorCpf(String cpfConta) {
        try {
            for (Conta conta : contas) {
                if (conta.getPessoa().getCpf().equals(cpfConta)) {
                    return conta;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Conta criarConta(String tipoConta, Pessoa pessoa) {
        if (!tipoConta.equals("corrente") && !tipoConta.equals("polpança") && !tipoConta.equals("salario")) {
            throw new IllegalArgumentException("Tipo de conta inválido");
        }

        Conta novaConta = null;
        Conta contaExistente = buscarContaPorCpf(pessoa.getCpf());

        if (contaExistente == null) {
            novaConta = criarNovaConta(tipoConta, pessoa);
            try {
                if (novaConta != null) {
                    SalvaNovaContaBanco(novaConta);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return novaConta;
    }

    public boolean contaJaExiste(String cpfConta) {
        return buscarContaPorCpf(cpfConta) != null;
    }

    private Conta criarNovaConta(String tipoConta, Pessoa pessoa) {
        List<String> dadosBancarios = dadosConta();
        String agencia = dadosBancarios.get(0);
        String conta = dadosBancarios.get(1);
        System.out.println("Agencia: " + agencia);
        System.out.println("conta: " + conta);
        Conta novaConta = null;
        switch (tipoConta) {
            case "corrente":
                novaConta = new ContaCorrente(agencia, conta, 0, pessoa);
                break;
            case "polpança":
                novaConta = new ContaPoupanca(agencia, conta, 0, pessoa);
                break;
            case "salario":
                novaConta = new ContaSalario(agencia, conta, 0, pessoa);
                break;
        }
        return novaConta;
    }

    private List<String> dadosConta() {
        List<String> lista = new ArrayList<>();
        Random random = new Random();
        int min = 000001; // valor mínimo de 6 dígitos
        int max = 999999; // valor máximo de 6 dígitos
        int randomNumbero = random.nextInt((max - min) + 1) + min;

        Conta Existente = buscarContaPorNumero(String.valueOf(randomNumbero));

        if (Existente != null) {
            do {
                randomNumbero = random.nextInt((max - min) + 1) + min;
                Existente = buscarContaPorNumero(String.valueOf(randomNumbero));
            } while (Existente == null);
        }

        int minA = 00; // valor mínimo de 6 dígitos
        int maxA = 99; // valor máximo de 6 dígitos
        int randomAgencia = random.nextInt((maxA - minA) + 1) + minA + 700;

        lista.add(String.valueOf(randomAgencia));
        lista.add(String.valueOf(randomNumbero));
        return lista;
    }

    public Conta atualizarConta(String numeroConta, Pessoa pessoa) {

//        if (!Objects.equals(numeroConta, conta.getNumero())) {
//            return null;
//        }

        Conta contaAtualizada = buscarContaPorNumero(numeroConta);

        if (Objects.equals(numeroConta, contaAtualizada.getNumero())) {
            contaAtualizada.setPessoa(pessoa);
            SalvarDadosBancoEdicao(contaAtualizada);
            return contaAtualizada;
        } else {
            return null;
        }

    }

    private void SalvarDadosBancoEdicao(Conta contaAlterada) {
        db.updateConta(contaAlterada);
        db.saveData();
    }

    private void SalvaNovaContaBanco(Conta novaConta) {
        contas.add(novaConta);
        db.saveData();
    }

    public boolean excluirConta(String numeroConta) {
        Conta contaExistente = buscarContaPorNumero(numeroConta);
        TransacaoService transacaoService = new TransacaoService();
        if (contaExistente != null) {
            if(transacaoService.verificaTransacoes(numeroConta)){
                contas.remove(contaExistente);
                SalvarDadosBancoExclusao();
                return true;
            }else{
                throw new IllegalArgumentException("Essa conta possui movimentações, não pode ser Excluída!");
            }
        }
        return false;
    }

    private void SalvarDadosBancoExclusao() {
        db.saveData();
    }

}
