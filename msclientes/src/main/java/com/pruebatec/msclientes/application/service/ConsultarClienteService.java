package com.pruebatec.msclientes.application.service;

import com.pruebatec.msclientes.domain.exception.ClienteNotFoundException;
import com.pruebatec.msclientes.domain.model.Cliente;
import com.pruebatec.msclientes.domain.ports.in.ConsultarClienteUseCase;
import com.pruebatec.msclientes.domain.ports.out.ClienteRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarClienteService implements ConsultarClienteUseCase {

    private final ClienteRepositoryPort repository;

    public ConsultarClienteService(ClienteRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    @Override
    public List<Cliente> listarTodos() {
        return repository.buscarTodos();
    }
}
