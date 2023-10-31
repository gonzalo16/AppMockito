package com.ifragoDevs.appMockito.spring.AppMockitoSpring;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cuenta;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class IntegracionJPATest {

    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById(){
        Optional<Cuenta> cuenta=cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Andres",cuenta.get().getPersona());
    }

    @Test
    void testFindByPersona(){
        Optional<Cuenta> cuenta=cuentaRepository.findByPersona("Jhon");
        assertTrue(cuenta.isPresent());
    }
}
