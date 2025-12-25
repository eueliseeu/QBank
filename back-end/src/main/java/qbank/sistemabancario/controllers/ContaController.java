package qbank.sistemabancario.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import qbank.sistemabancario.context.conta.Conta;
import qbank.sistemabancario.context.pessoa.Pessoa;
import qbank.sistemabancario.dto.ContaService;

//import static qbank.sistemabancario.dto.ContaService.criarConta;

@Controller("/user")
public class ContaController {
    //ContaService contaService = new ContaService();

    @Inject
    ContaService contaService;

    @Post(value = "/new/{tipoConta}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.TEXT_PLAIN)
    HttpResponse<String> criarNovaConta(@Body Pessoa dadosPessoais,
                                        @PathVariable("tipoConta") String tipoConta) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "";
        try {
            if (contaService.contaJaExiste(dadosPessoais.getCpf())) {
                return HttpResponse.status(409, "Conta já existe");
            }
            Conta novaConta = contaService.criarConta(tipoConta, dadosPessoais);
            if (novaConta == null) {
                return HttpResponse.badRequest("Tipo de conta inválido ou dados incorretos");
            } else {
                jsonResult = mapper.writeValueAsString(novaConta);
                return HttpResponse.ok(jsonResult);
            }
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest("Argumentos inconsistentes: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.serverError("Erro ao processar a requisição");
        }
    }

    @Put(value = "/edit/{numeroConta}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.TEXT_PLAIN)
    HttpResponse<String> editarDadosUsuarioConta(@Body Pessoa dadosAtualizados,
                                                 @PathVariable("numeroConta") String numeroConta) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult;
        try {
            Conta contaAtualizada = contaService.atualizarConta(numeroConta, dadosAtualizados);
            if (contaAtualizada == null) {
                return HttpResponse.badRequest("Dados incorretos");
            } else {
                jsonResult = mapper.writeValueAsString(contaAtualizada);
                return HttpResponse.ok(jsonResult);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.serverError("Erro ao converter transações em JSON");
        }

    }

    @Get(value = "/search/{numeroConta}", produces = MediaType.TEXT_PLAIN)
    HttpResponse<String> buscarClientePorConta(@PathVariable("numeroConta") String numeroConta) {
        Conta conta = contaService.buscarContaPorNumero(numeroConta);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult;
        try {
            jsonResult = mapper.writeValueAsString(conta.getPessoa());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.serverError("Erro ao converter transações em JSON");
        }

        return HttpResponse.ok(jsonResult);
    }

    @Delete(value = "/delete/{numeroConta}", produces = MediaType.TEXT_PLAIN)
    HttpResponse<String> excluirConta(@PathVariable("numeroConta") String numeroConta) {
        try {
            boolean contaExcluida = contaService.excluirConta(numeroConta);
            if (contaExcluida) {
                return HttpResponse.ok("Conta excluída com sucesso");
            } else {

                return HttpResponse.notFound("Conta não encontrada");
            }
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest("Argumentos inconsistentes: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.serverError("Erro ao excluir a conta");
        }
    }
}
