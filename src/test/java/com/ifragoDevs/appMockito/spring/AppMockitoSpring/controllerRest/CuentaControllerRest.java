package com.ifragoDevs.appMockito.spring.AppMockitoSpring.controllerRest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.dto.TransaccionDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CuentaControllerRest {

    @Autowired
    private TestRestTemplate cliente;



    private ObjectMapper objectMapper;
    @LocalServerPort
    private int puerto;


    @BeforeEach
    void setUp(){
        objectMapper=new ObjectMapper();
    }


    @Test
    @Order(1)
    void testTransferir() throws JsonProcessingException {
        TransaccionDto dto=new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal(100));

        /*
        * Simulación de cliente rest
        * postForEntity recibe (url del endpoint del controlador)
        *                      (objeto que enviamos en el request)
        *                      (tipo de objeto del que queremos la respuesta)
        * */
        ResponseEntity<String> response = cliente.postForEntity(crearUri("/api/cuentas/transferir"), dto, String.class);

        /*
        * Comparamos los datos obtenidos de la respuesta
        * */

        String json=response.getBody();
        System.out.println(puerto+json);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
        assertNotNull(json);
        assertTrue(json.contains("Transferencia realizada con éxito"));

        /*
        * Otra forma de probar los atributos de un objeto json
        * */
        JsonNode jsonNode = objectMapper.readTree(json);
        assertEquals("Transferencia realizada con éxito",jsonNode.path("mensaje").asText());
        assertEquals(LocalDate.now().toString(),jsonNode.path("date").asText());
    }

    private String crearUri(String uri){
        return "http://localhost:"+puerto+uri;
    }
}
