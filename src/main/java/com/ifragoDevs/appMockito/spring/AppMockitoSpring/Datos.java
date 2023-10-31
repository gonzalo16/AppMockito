package com.ifragoDevs.appMockito.spring.AppMockitoSpring;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Banco;
import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class Datos {

    public static final Cuenta cuenta_001=new Cuenta(1L,"Gonzalo",new BigDecimal("1000"));

    public static final Cuenta cuenta_002=new Cuenta(2L,"Franco",new BigDecimal("1200"));

    public static final Banco banco_001=new Banco(1L,"Banco Santander",0);

    public static Optional<Cuenta> crearCuenta1(){
        return Optional.of(new Cuenta(1L,"Gonzalo",new BigDecimal("1000")));
    }

    public static Optional<Cuenta> crearCuenta2(){
        return Optional.of(new Cuenta(2L,"Franco",new BigDecimal("2000")));
    }

    public static Optional<Banco> crearBanco(){
        return Optional.of(new Banco(23L,"Santander S.A",0));
    }
}

