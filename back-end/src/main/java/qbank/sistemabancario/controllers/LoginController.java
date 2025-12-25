package qbank.sistemabancario.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import qbank.sistemabancario.context.conta.Conta;
import qbank.sistemabancario.context.conta.ContaParaTransacao;
import qbank.sistemabancario.dto.ContaService;


@Controller("/login")
public class LoginController {
    //ContaService contaService = new ContaService();

    @Inject
    ContaService contaService;

    @Get(value = "/{numeroConta}", produces = MediaType.TEXT_PLAIN)
    HttpResponse<String> realizarLogin( @PathVariable("numeroConta") String numeroConta) {

        Conta conta = contaService.buscarContaPorNumero(numeroConta);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult;
        try {
            jsonResult = mapper.writeValueAsString(conta);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.serverError("Erro ao converter transações em JSON");
        }

        return HttpResponse.ok(jsonResult);
    }

}
