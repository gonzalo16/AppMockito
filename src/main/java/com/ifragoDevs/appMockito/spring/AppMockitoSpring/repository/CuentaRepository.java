package com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta,Long> {

    @Query(value = "select * from cuentas where persona=?1",nativeQuery = true)
    Optional<Cuenta> findByPersona(String persona);
}
