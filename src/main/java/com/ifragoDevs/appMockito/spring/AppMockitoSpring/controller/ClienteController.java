package com.ifragoDevs.appMockito.spring.AppMockitoSpring.controller;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cliente;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
public class ClienteController {


    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> crearCliente(@RequestBody Cliente c){
        Optional<Cliente> optionalCliente=Optional.of(clienteService.crearCliente(c));
        Map<String,Object> response=new HashMap<>();
        if(optionalCliente.isPresent()){
            response.put("mensaje","Cliente creado con éxito");
            response.put("codigo",HttpStatus.CREATED);
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.ofNullable(optionalCliente);
        }
    }
}
