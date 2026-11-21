package com.pruebatec.msclientes.domain.ports.out;

import com.pruebatec.msclientes.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente guardar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    List<Cliente> buscarTodos();
}
