package com.pruebatec.msclientes.domain.ports.in;

import com.pruebatec.msclientes.domain.model.Cliente;

import java.util.List;

public interface ConsultarClienteUseCase {
    Cliente obtenerPorId(Long id);
    List<Cliente> listarTodos();
}
