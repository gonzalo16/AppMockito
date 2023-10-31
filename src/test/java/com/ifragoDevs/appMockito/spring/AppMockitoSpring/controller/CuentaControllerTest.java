package com.ifragoDevs.appMockito.spring.AppMockitoSpring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.Datos;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.dto.TransaccionDto;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cuenta;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {

    @Autowired
    private MockMvc mvc;


    private ObjectMapper mapeador;

    @MockBean
    private CuentaService cuentaService;


    @BeforeEach
    void setUp() {
        mapeador=new ObjectMapper();
    }

    @Test
    void testDetalleCuenta() throws Exception {
        when(cuentaService.findById(anyLong())).thenReturn(Datos.crearCuenta1().orElseThrow());

        mvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("Gonzalo"))
                .andExpect(jsonPath("$.saldo").value("1000"));


    }

    @Test
    void testTransferir() throws Exception {

        TransaccionDto transaccionPrueba=new TransaccionDto();
        transaccionPrueba.setCuentaOrigenId(1L);
        transaccionPrueba.setCuentaDestinoId(2L);
        transaccionPrueba.setBancoId(1L);
        transaccionPrueba.setMonto(new BigDecimal(500));

        System.out.println(mapeador.writeValueAsString(transaccionPrueba));

        Map<String,Object> response=new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status","OK");
        response.put("mensaje","Transferencia realizada con éxito");
        response.put("transaccion",transaccionPrueba);

        System.out.println(mapeador.writeValueAsString(response));


        mvc.perform(post("/api/cuentas/transferir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapeador.writeValueAsString(transaccionPrueba)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con éxito"))
                .andExpect(jsonPath("$.transaccion.cuentaOrigenId").value(1L))
                .andExpect(content().json(mapeador.writeValueAsString(response)));
    }

    @Test
    void testGuardarCuenta() throws Exception {

        //Datos para testear en nuestro caso un objeto de tipo cuenta
        Cuenta cuentaPrueba=new Cuenta(null,"Pepe",new BigDecimal("3000"));
        when(cuentaService.save(any())).then(invocation -> {
            Cuenta cuentaObtenida=invocation.getArgument(0);
            cuentaObtenida.setId(3L);
            return cuentaObtenida;
        });

        mvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
                .content(mapeador.writeValueAsString(cuentaPrueba)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3L));
    }


}

