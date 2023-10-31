package com.ifragoDevs.appMockito.spring.AppMockitoSpring.services;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cliente;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
