package com.pruebatec.msclientes.domain.ports.in;

import com.pruebatec.msclientes.domain.model.Direccion;

public interface GestionarDireccionesUseCase {
    Direccion agregarDireccion(Long clienteId, Direccion direccion);
    void eliminarDireccion(Long clienteId, Long direccionId);
}
