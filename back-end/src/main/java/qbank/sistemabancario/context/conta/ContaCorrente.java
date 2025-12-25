package qbank.sistemabancario.context.conta;

import io.micronaut.serde.annotation.Serdeable;
import qbank.sistemabancario.context.pessoa.Pessoa;

import java.math.BigDecimal;

@Serdeable
public class ContaCorrente extends Conta {
    public ContaCorrente() {
        super(null, null, null, null, "Corrente");
    }

    public ContaCorrente(String agencia, String numero, float saldo, Pessoa pessoa) {
        super(agencia, numero, BigDecimal.valueOf(saldo), pessoa, "Corrente");
    }

}
