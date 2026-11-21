package com.pruebatec.msclientes.application.service;

import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.ports.in.CrearClienteUseCase;
import com.pruebatec.msclientes.domain.ports.out.ClienteRepositoryPort;
import org.springframework.stereotype.Service;


@Service
public class CrearClienteService implements CrearClienteUseCase {

    private final ClienteRepositoryPort repository;

    public CrearClienteService(ClienteRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Cliente crear(Cliente cliente) {
        return repository.guardar(cliente);
    }
}
