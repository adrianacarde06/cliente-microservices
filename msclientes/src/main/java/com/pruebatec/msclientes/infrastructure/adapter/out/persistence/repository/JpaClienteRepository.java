package com.pruebatec.msclientes.infrastructure.adapter.out.persistence.repository;

import com.pruebatec.msclientes.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
