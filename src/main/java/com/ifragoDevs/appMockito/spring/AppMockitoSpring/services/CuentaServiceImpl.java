package com.ifragoDevs.appMockito.spring.AppMockitoSpring.services;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Banco;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cuenta;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository.BancoRepository;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository cuentaRepository;

    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElseThrow();
    }

    @Override
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        cuentaRepository.deleteById(id);
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransferencia(Long idBanco) {
        Banco banco=bancoRepository.findById(idBanco).orElseThrow();
        return banco.getTotalTransferencia();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long idCuenta) {
        Cuenta cuenta=cuentaRepository.findById(idCuenta).orElseThrow();
        return cuenta.getSaldo();
    }

    @Override
    @Transactional
    public void transferir(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto,Long idBanco) {
        Banco banco=bancoRepository.findById(idBanco).orElseThrow();
        int totalTransferencias=banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencias);
        bancoRepository.save(banco);

        Cuenta cuentaOrigen=cuentaRepository.findById(idCuentaDestino).orElseThrow();
        cuentaOrigen.debito(monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino=cuentaRepository.findById(idCuentaDestino).orElseThrow();
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);
    }
}
