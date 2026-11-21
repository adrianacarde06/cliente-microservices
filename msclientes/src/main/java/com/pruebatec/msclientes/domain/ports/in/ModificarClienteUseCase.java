package com.pruebatec.msclientes.domain.ports.in;

import com.pruebatec.msclientes.domain.model.Cliente;

public interface ModificarClienteUseCase {
    Cliente modificar(Long id, Cliente cliente);
}
