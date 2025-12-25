package qbank.sistemabancario.dto;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import qbank.sistemabancario.context.conta.ContaParaTransacao;
import qbank.sistemabancario.strategy.transacao.TED;
import qbank.sistemabancario.strategy.transacao.Transacao;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;


@MicronautTest
public class TransacaoServiceTest {
    @Inject
    @Client("/transacoes")
    HttpClient client;


    @Factory
    @Replaces(TransacaoService.class)
    static class MockTransacaoFactory{
        @Singleton
        TransacaoService mockTransacaoService(){
            TransacaoService mock = Mockito.mock(TransacaoService.class);
            ContaParaTransacao conta1 = new ContaParaTransacao("123","123456");
            ContaParaTransacao conta2 = new ContaParaTransacao("123","789102");
            Transacao transacao1 = new Transacao(conta1,conta2,new BigDecimal("1000"), Transacao.TipoTransacao.TED);
            Transacao transacao2 = new Transacao(conta2,conta1,new BigDecimal("3000"), Transacao.TipoTransacao.PIX);
            transacao1.setId("445566");
            transacao1.setDataHora("2024-12-05T16:36:50.202396600");
            transacao2.setId("667788");
            transacao2.setDataHora("2024-12-05T16:45:06.705177700");


            List<Transacao> transacoes = List.of(transacao1, transacao2);
            when(mock.ExtratoDeTransacoes(anyString())).thenReturn(transacoes);
            return mock;
        }
    }


    @Test
    void buscarExtrato() {
        HttpRequest<String> request = HttpRequest.GET("/extrato/929601");
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        assertNotNull(response.body(),"A resposta não pode ser Null.");

        String expectedResponse = "[{\"id\":\"445566\",\"contaOrigem\":{\"agencia\":\"123\",\"numero\":\"123456\"},\"contaDestino\":{\"agencia\":\"123\",\"numero\":\"789102\"},\"valor\":1000,\"dataHora\":\"2024-12-05T16:36:50.202396600\",\"tipo\":\"TED\"},{\"id\":\"667788\",\"contaOrigem\":{\"agencia\":\"123\",\"numero\":\"789102\"},\"contaDestino\":{\"agencia\":\"123\",\"numero\":\"123456\"},\"valor\":3000,\"dataHora\":\"2024-12-05T16:45:06.705177700\",\"tipo\":\"PIX\"}]";
        assertEquals(expectedResponse, response.body());
    }
}
