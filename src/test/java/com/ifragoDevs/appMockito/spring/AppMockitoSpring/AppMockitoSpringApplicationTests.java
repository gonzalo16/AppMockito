package com.ifragoDevs.appMockito.spring.AppMockitoSpring;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository.BancoRepository;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository.CuentaRepository;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.services.CuentaService;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.services.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class AppMockitoSpringApplicationTests {

	@MockBean
	private BancoRepository bancoRepository;

	@MockBean
	private CuentaRepository cuentaRepository;

	@Autowired
	private CuentaService cuentaService;


	@Test
	void contextLoads() {

		when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta1());
		when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco());

		BigDecimal saldoOrigen=cuentaService.revisarSaldo(1L);
		assertEquals("1000",saldoOrigen.toString());
	}

}
