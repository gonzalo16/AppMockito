package com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {


}
