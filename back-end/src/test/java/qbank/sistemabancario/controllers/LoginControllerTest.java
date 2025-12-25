//package qbank.sistemabancario.controllers;
//
//import io.micronaut.context.annotation.Factory;
//import io.micronaut.context.annotation.Replaces;
//import io.micronaut.http.HttpRequest;
//import io.micronaut.http.HttpResponse;
//import io.micronaut.http.client.HttpClient;
//import io.micronaut.http.client.annotation.Client;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
//import jakarta.inject.Inject;
//import jakarta.inject.Singleton;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import qbank.sistemabancario.context.conta.Conta;
//import qbank.sistemabancario.context.conta.ContaSalario;
//import qbank.sistemabancario.context.pessoa.Pessoa;
//import qbank.sistemabancario.dto.ContaService;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@MicronautTest
//public class LoginControllerTest {
//
//    @Inject
//    @Client("/")
//    HttpClient client;
//
//    @Inject
//    ContaService contaService;
//
//    @Factory
//    @Replaces(ContaService.class)
//    @Singleton
//    static class MockContaServiceFactory {
//        @Singleton
//        ContaService mockContaService() {
//            ContaService mock = Mockito.mock(ContaService.class);
//            Pessoa pessoa = new Pessoa("Tio Paulo", "3131314123", "12313", "mateus@gmail.com", "123412314");
//            Conta conta = new ContaSalario("111", "555555", 2000.0f, pessoa);
//            when(mock.buscarContaPorNumero(anyString())).thenReturn(conta);
//            return mock;
//        }
//    }
//
//    @Test
//    void testRealizarLogin() {
//        HttpRequest<String> request = HttpRequest.GET("/login/555555");
//        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
//
//        // Verifique se a resposta não é null
//        assertNotNull(response.body(), "A resposta não pode ser null.");
//
//        String expectedResponse = "{\"tipoConta\":\"Salário\",\"agencia\":\"111\",\"numero\":\"555555\",\"saldo\":2000.0,\"pessoa\":{\"nome\":\"Tio Paulo\",\"cpf\":\"3131314123\",\"rg\":\"12313\",\"email\":\"mateus@gmail.com\",\"telefone\":\"123412314\"}}";
//        assertEquals(expectedResponse, response.body());
//    }
//}
