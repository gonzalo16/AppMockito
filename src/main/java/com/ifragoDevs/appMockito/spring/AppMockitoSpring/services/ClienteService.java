package com.ifragoDevs.appMockito.spring.AppMockitoSpring.services;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cliente;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {

    Cliente crearCliente(Cliente cliente);
}
