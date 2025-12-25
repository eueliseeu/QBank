package qbank.sistemabancario.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import qbank.sistemabancario.dto.ContaService;
import qbank.sistemabancario.dto.Requests.MensageException;
import qbank.sistemabancario.dto.TransacaoService;
import qbank.sistemabancario.strategy.transacao.Transacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller("/transacoes")
public class TransacoesController {
    //TransacaoService transacaoService = new TransacaoService();
//    @Inject
//    TransacaoService transacaoService;

    private final TransacaoService transacaoService;

    @Inject
    public TransacoesController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Post(value = "/deposito/{numeroConta}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.TEXT_PLAIN)
    HttpResponse<String> realizarDeposito(@Body Transacao deposito,
                                          @PathVariable("numeroConta") String numeroConta) {

        if (!Objects.equals(numeroConta, deposito.getContaOrigem().getNumero()))
            return HttpResponse.badRequest("Dados incompativeis");

        try {
            String jsonResult = transacaoService.realizarTransaco(deposito);
            return HttpResponse.ok("tranferencia " + jsonResult + " concluida com sucesso!");
        } catch (MensageException e) {
            return HttpResponse.badRequest(e.getMessage());
            // Retorna 400 Bad Request com a mensagem de erro
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.serverError("Erro ao converter transações em JSON");
        }
    }

    @Get(value = "/{numeroTransacao}", consumes = MediaType.APPLICATION_JSON)
    HttpResponse<String> verificarTransacao(@PathVariable String numeroTransacao) {

        Transacao transacao = transacaoService.buscarTransacoesPorNumero(numeroTransacao);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult;
        try {
            jsonResult = mapper.writeValueAsString(transacao);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.serverError("Erro ao converter transações em JSON");
        }

        return HttpResponse.ok(jsonResult);

    }

    @Get(value = "/extrato/{numeroConta}", consumes = MediaType.APPLICATION_JSON)
    HttpResponse<String> extratoDeTransacoes(@PathVariable String numeroConta) {
        List<Transacao> transacoes = transacaoService.ExtratoDeTransacoes(numeroConta);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult;
        try {
            jsonResult = mapper.writeValueAsString(transacoes);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.serverError("Erro ao converter transações em JSON");
        }

        return HttpResponse.ok(jsonResult);
    }

}
