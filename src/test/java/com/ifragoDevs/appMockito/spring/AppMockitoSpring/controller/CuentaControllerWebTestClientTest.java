package com.ifragoDevs.appMockito.spring.AppMockitoSpring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.dto.TransaccionDto;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuentaControllerWebTestClientTest {

    @Autowired
    private WebTestClient testClient;

    private ObjectMapper mapeador;

    @BeforeEach
    void setUp() {
        mapeador=new ObjectMapper();
    }

    @Test
    @Order(1)
    void testTransferir() throws JsonProcessingException {
        TransaccionDto dto=new TransaccionDto();
            dto.setCuentaOrigenId(1L);
            dto.setCuentaDestinoId(2L);
            dto.setBancoId(1L);
            dto.setMonto(new BigDecimal(100));

        Map<String,Object> response=new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status","OK");
        response.put("mensaje","Transferencia realizada con éxito");
        response.put("transaccion",dto);

            testClient.post().uri("http://localhost:8080/api/cuentas/transferir")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dto)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody().jsonPath("$.mensaje").isNotEmpty()
                    .jsonPath("$.mensaje").value(is("Transferencia realizada con éxito"))
                    .jsonPath("$.mensaje").value(valor -> assertEquals("Transferencia realizada con éxito",valor))
                    .json(mapeador.writeValueAsString(response));
        System.out.println("Json request: " +mapeador.writeValueAsString(dto));
        System.out.println("Json response: " +mapeador.writeValueAsString(response));

    }


    @Test
    @Order(3)
    void testDetalleDeserializado(){
        testClient.get().uri("http://localhost:8080/api/cuentas/2").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Cuenta.class)
                    .consumeWith(response -> {
                        Cuenta cuentaObtenida=response.getResponseBody();

                        //Comprobamos los atributos del objeto obtenido
                        assertEquals("Jhon",cuentaObtenida.getPersona());
                        assertEquals("2000.00",cuentaObtenida.getSaldo().toString());
                    });

    }


    @Test
    @Order(4)
    void testTransferir2() throws JsonProcessingException {

        //Objeto que tenemos que enviar en el body del request
        TransaccionDto dto=new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal(100));

        //Respuesta del endpoint
        Map<String,Object> response=new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status","OK");
        response.put("mensaje","Transferencia realizada con éxito");
        response.put("transaccion",dto);

        testClient.post().uri("http://localhost:8080/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .consumeWith(respuesta -> {
                        try {
                            JsonNode node=mapeador.readTree(respuesta.getResponseBody());
                            assertEquals("Transferencia realizada con éxito",node.path("mensaje").asText());
                            System.out.println(node.path("date").toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

    }

    @Test
    void testListarCuentas(){
        testClient.get().uri("/api/cuentas").exchange()
                .expectStatus().isOk()
                .expectBodyList(Cuenta.class)
                .hasSize(2);
    }


    @Test
    void testBorrarCuenta(){
        testClient.get().uri("/api/cuentas")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cuenta.class)
                                .hasSize(2);
        testClient.delete().uri("http://localhost:8080/api/cuentas/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        testClient.get().uri("http://localhost:8080/api/cuentas/3")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();

    }

    @Test
    void testGuardarCuenta(){
        Cuenta cuenta = new Cuenta(null, "Pepe", new BigDecimal("3000"));

        //When
        testClient.post().uri("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cuenta)
                .exchange()

                    //Then
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(3);
    }
}