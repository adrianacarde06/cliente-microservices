package com.pruebatec.msclientes.domain.ports.in;

import com.pruebatec.msclientes.domain.model.Cliente;

public interface CrearClienteUseCase {
    Cliente crear(Cliente cliente);
}
