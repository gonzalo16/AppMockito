package com.ifragoDevs.appMockito.spring.AppMockitoSpring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerWebTestClientTest {

    @Autowired
    private WebTestClient testClient;

    private ObjectMapper mapeador;

    @BeforeEach
    void setUp() {
        mapeador=new ObjectMapper();
    }

    @Test
    void testCrearCliente(){
        Cliente c=new Cliente("X34642355J","Ivan");

        testClient.post().uri("http://localhost:8080/api/cuentas/crear").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(c).exchange()
                .expectStatus().isOk();
    }
}
