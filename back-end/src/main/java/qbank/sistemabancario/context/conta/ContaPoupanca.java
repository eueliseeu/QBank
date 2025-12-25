package qbank.sistemabancario.context.conta;

import io.micronaut.serde.annotation.Serdeable;
import qbank.sistemabancario.context.pessoa.Pessoa;

import java.math.BigDecimal;

@Serdeable
public class ContaPoupanca extends Conta {

    public ContaPoupanca() {
        super(null, null, null, null, "Poupança");
    }

    public ContaPoupanca(String agencia, String numero, float saldo, Pessoa pessoa) {
        super(agencia, numero, BigDecimal.valueOf(saldo), pessoa, "Poupança");
    }

}
