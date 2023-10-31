package com.ifragoDevs.appMockito.spring.AppMockitoSpring.repository;

import com.ifragoDevs.appMockito.spring.AppMockitoSpring.entity.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BancoRepository extends JpaRepository<Banco,Long> {

}
