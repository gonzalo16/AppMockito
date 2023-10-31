package com.ifragoDevs.appMockito.spring.AppMockitoSpring.controller;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.dto.TransaccionDto;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cuenta;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Cuenta cuenta=null;
        try{
            cuenta=cuentaService.findById(id);
        }catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDto dto){
        cuentaService.transferir(dto.getCuentaOrigenId(), dto.getCuentaDestinoId(), dto.getMonto(), dto.getBancoId());

        Map<String,Object> response=new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status","OK");
        response.put("mensaje","Transferencia realizada con éxito");
        response.put("transaccion",dto);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta guardar(@RequestBody Cuenta cuenta){
        return cuentaService.save(cuenta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCuenta(@PathVariable Long id){
        Optional<Cuenta> cuentaABorrar=Optional.of(cuentaService.findById(id));
        System.out.println(cuentaABorrar.get());
        cuentaService.deleteById(id);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cuenta> listar(){
        return cuentaService.findAll();
    }
}
