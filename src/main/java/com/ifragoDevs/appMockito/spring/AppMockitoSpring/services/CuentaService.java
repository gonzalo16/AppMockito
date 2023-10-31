package com.ifragoDevs.appMockito.spring.AppMockitoSpring.services;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    Cuenta findById(Long id);

    Cuenta save(Cuenta cuenta);

    void deleteById(Long id);

    List<Cuenta> findAll();

    int revisarTotalTransferencia(Long idBanco);

    BigDecimal revisarSaldo(Long idCuenta);

    void transferir(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto,Long idBanco);
}
